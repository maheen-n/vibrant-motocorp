package com.motorcorp.application.modules.user.dao;

import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.application.modules.user.models.UserFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserDao {
    void save(User user);

    User findById(Long id);

    Page<User> findAll(UserFilter filter);

    Optional<User> findUserByUserNameExitsInEmailOrPhone(String userName);

    Optional<User> findUserByEmail(String emailId);

    Optional<User> findUserByPhone(String phone);
}
