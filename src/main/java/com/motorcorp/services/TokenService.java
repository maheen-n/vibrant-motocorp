package com.motorcorp.services;

import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.models.AuthToken;
import com.motorcorp.models.UserSession;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    UserSession validateTokenWithUser(String token, UserDetails userDetails);

    AuthToken createTokenForUser(UserDetails userDetails, User user);

    String getUsernameFromToken(String token);

    String getUserIdFromToken(String token);
}
