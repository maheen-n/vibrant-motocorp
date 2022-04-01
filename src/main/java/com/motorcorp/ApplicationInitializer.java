package com.motorcorp;

import com.motorcorp.application.enums.Role;
import com.motorcorp.application.modules.user.dao.UserDao;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.enums.EntityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserDao userDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createUserIfNotFound("admin", Role.ADMIN);
    }

    private void createUserIfNotFound(String name, Role role) {
        userDao.findUserByUserNameExitsInEmailOrPhone("admin@gmail.com")
                .orElseGet(() -> {
                    User user = new User();
                    user.setName(name);
                    user.setEmail("admin@gmail.com");
                    user.setRole(role);
                    user.setPassword("$2a$10$8D4fbM6HGxFkBbN2WMXeEe00XBulnii6/YoB/X/B1AL3NqJi6T2mW");
                    user.setStatus(EntityStatus.ACTIVE);
                    userDao.save(user);
                    return user;
                });
    }
}
