package com.motorcorp.services;

import com.motorcorp.models.AuthToken;
import com.motorcorp.models.UserSession;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    AuthToken login(String userName, String password);

    AuthToken validateToken(String userToken);

    UserSession validateTokenWithUser(AuthToken authToken, UserDetails userDetails);

    AuthToken logout(UserSession userSession);

}
