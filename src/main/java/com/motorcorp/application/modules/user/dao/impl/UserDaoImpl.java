package com.motorcorp.application.modules.user.dao.impl;

import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import com.motorcorp.application.modules.user.dao.UserDao;
import com.motorcorp.application.modules.user.dao.UserSpecifications;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.application.modules.user.models.UserFilter;
import com.motorcorp.models.BaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserDaoImpl extends UserSpecifications<User> implements UserDao {
    @Autowired
    private UserRepository userRepo;

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public Page<User> findAll(UserFilter filter) {
        Specification<User> specification = Specification.where(
                query(filter.getQuery())
                        .and(date(filter.getDateFilter()))
                        .and(entityStatus(filter.getStatus()))
                        .and(include(filter.getInclude()))
                        .and(exclude(filter.getExclude()))
        );
        return userRepo.findAll(specification,filter.filterPageable());
    }

    @Override
    public Optional<User> findUserByEmail(String emailId) {
        return userRepo.findUserByEmail(emailId);
    }

    @Override
    public Optional<User> findUserByPhone(String phone) {
        return userRepo.findUserByPhone(phone);
    }

    @Override
    public Optional<User> findUserByUserNameExitsInEmailOrPhone(String userName) {
        return userRepo.findUserByEmailOrPhone(userName, userName);
    }
}
