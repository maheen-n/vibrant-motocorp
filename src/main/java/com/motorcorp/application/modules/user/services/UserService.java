package com.motorcorp.application.modules.user.services;

import com.motorcorp.application.modules.user.models.PasswordUpdate;
import com.motorcorp.application.modules.user.models.UserBasicView;
import com.motorcorp.application.modules.user.models.UserCreateRequest;
import com.motorcorp.application.modules.user.models.UserFilter;
import com.motorcorp.application.modules.user.models.UserUpdateRequest;
import com.motorcorp.application.modules.user.models.UserView;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;

public interface UserService {
    UserView create(UserCreateRequest request);

    UserView get(Long id);

    PaginatedList<UserView> get(UserFilter filter);

    UserView update(Long id, UserUpdateRequest updateRequest);

    UserView delete(Long id);

    UserBasicView getBasicInfo(Long id);

    UserView updatePassword(Long id, PasswordUpdate update);
}
