package com.motorcorp.application.modules.user.services.impl;

import com.motorcorp.application.modules.user.dao.UserDao;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.application.modules.user.models.PasswordUpdate;
import com.motorcorp.application.modules.user.models.UserBasicView;
import com.motorcorp.application.modules.user.models.UserCreateRequest;
import com.motorcorp.application.modules.user.models.UserFilter;
import com.motorcorp.application.modules.user.models.UserUpdateRequest;
import com.motorcorp.application.modules.user.models.UserView;
import com.motorcorp.application.modules.user.services.UserService;
import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.exceptions.ServiceException;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.motorcorp.application.exceptions.ErrorCodeEnum.USER_EMAIL_ALREADY_EXISTS;
import static com.motorcorp.application.exceptions.ErrorCodeEnum.USER_NOT_FOUND;
import static com.motorcorp.application.exceptions.ErrorCodeEnum.USER_PHONE_ALREADY_EXISTS;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserView create(UserCreateRequest request) {
        if (StringUtils.isNotBlank(request.getEmail()) && userDao.findUserByEmail(request.getEmail()).isPresent())
            throw new ServiceException(USER_EMAIL_ALREADY_EXISTS);
        if (StringUtils.isNotBlank(request.getPhone()) && userDao.findUserByPhone(request.getPhone()).isPresent())
            throw new ServiceException(USER_PHONE_ALREADY_EXISTS);
        User user = request.getEntity();
        user.setStatus(EntityStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userDao.save(user);
        return user.getView(UserView.class);
    }

    @Transactional(readOnly = true)
    @Override
    public UserView get(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            log.info("user fetch for {} not found", id);
            throw new ServiceException(USER_NOT_FOUND);
        }
        return user.getView(UserView.class);
    }

    @Override
    public UserBasicView getBasicInfo(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            log.info("user fetch for {} not found", id);
            throw new ServiceException(USER_NOT_FOUND);
        }
        return user.getView(UserBasicView.class);
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedList<UserView> get(UserFilter filter) {
        Page<User> page = userDao.findAll(filter);
        return new PaginatedList<>(page, UserView.class);
    }

    @Transactional
    @Override
    public UserView update(Long id, UserUpdateRequest updateRequest) {
        User user = userDao.findById(id);
        if (user == null) {
            log.info("user fetch for {} not found", id);
            throw new ServiceException(USER_NOT_FOUND);
        }
        int hash = user.hashCode();
        updateRequest.update(user);
        if (hash == user.hashCode()) throw new ServiceException(CommonErrorCodeEnum.NOT_MODIFIED);
        userDao.save(user);
        return user.getView(UserView.class);
    }

    @Override
    public UserView updatePassword(Long id, PasswordUpdate update) {
        if (StringUtils.equals(update.getNewPassword(), update.getRepeatPassword())) {
            User user = userDao.findById(id);
            if (user == null) {
                log.info("user fetch for {} not found", id);
                throw new ServiceException(USER_NOT_FOUND);
            }
            user.setPassword(passwordEncoder.encode(update.getNewPassword()));
            userDao.save(user);
            return user.getView(UserView.class);
        } else throw new ServiceException(CommonErrorCodeEnum.BAD_REQUEST);
    }

    @Transactional
    @Override
    public UserView delete(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            log.info("user fetch for {} not found", id);
            throw new ServiceException(USER_NOT_FOUND);
        }
        user.setStatus(EntityStatus.DELETED);
        userDao.save(user);
        return user.getView(UserView.class);
    }
}
