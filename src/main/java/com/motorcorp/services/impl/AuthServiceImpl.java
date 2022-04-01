package com.motorcorp.services.impl;

import com.motorcorp.application.exceptions.ErrorCodeEnum;
import com.motorcorp.application.modules.user.dao.UserDao;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.exceptions.AuthenticationException;
import com.motorcorp.exceptions.ServiceException;
import com.motorcorp.models.AuthToken;
import com.motorcorp.models.UserSession;
import com.motorcorp.services.AuthService;
import com.motorcorp.services.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthToken login(String userName, String password) {
        User user = validate(userName, password);
        if (user != null) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return tokenService.createTokenForUser(userDetails, user);
        }
        throw new ServiceException(ErrorCodeEnum.USER_NOT_FOUND_OR_ACTIVE);
    }

    @Override
    public AuthToken validateToken(String userToken) {
        try {
            String userName = tokenService.getUsernameFromToken(userToken);
            if (StringUtils.isBlank(userName)) throw new AuthenticationException("Invalid Token");
            String userIdFromToken = tokenService.getUserIdFromToken(userToken);
            if (StringUtils.isBlank(userIdFromToken)) throw new AuthenticationException("Invalid Access");
            User user = userDao.findById(Long.valueOf(userIdFromToken));
            if (user == null || !user.getStatus().equals(EntityStatus.ACTIVE))
                throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
            return new AuthToken(user.getId(), userToken, userName);
        } catch (Exception e) {
            throw new AuthenticationException(e.getLocalizedMessage());
        }
    }

    @Override
    public UserSession validateTokenWithUser(AuthToken authToken, UserDetails userDetails) {
        return tokenService.validateTokenWithUser(authToken.getToken(), userDetails);
    }

    @Override
    public AuthToken logout(UserSession userSession) {
        return null;
    }

    User validate(String userName, String password) {
        User user = userDao.findUserByUserNameExitsInEmailOrPhone(userName).orElse(null);
        if (user == null || !user.getStatus().equals(EntityStatus.ACTIVE)) return null;
        return user;
    }

}
