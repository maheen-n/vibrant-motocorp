package com.motorcorp.services.impl;

import com.motorcorp.application.enums.Role;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.models.AuthToken;
import com.motorcorp.models.UserSession;
import com.motorcorp.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenServiceImpl implements TokenService {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public AuthToken createTokenForUser(UserDetails userDetails, User user) {
        Map<String, Object> claims = new HashMap<>();
        String tokenForUser = doGenerateToken(claims, userDetails.getUsername(), user.getId());
        return new AuthToken(user.getId(), tokenForUser, user.getName(), user.getRole().toString(), JWT_TOKEN_VALIDITY * 1000);
    }

    @Override
    public UserSession validateTokenWithUser(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final String userIdFromToken = getUserIdFromToken(token);
        if (!username.equals(userDetails.getUsername()) && Boolean.TRUE.equals(isTokenExpired(token))) return null;
        return new UserSession(Long.valueOf(userIdFromToken), token, username, Role.valueOf(userDetails.getAuthorities().toArray()[0].toString()));
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject, Long userId) {
        return Jwts.builder().setClaims(claims)
                .setId(String.valueOf(userId))
                .setIssuer("Vibrant Moto Corp")
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
