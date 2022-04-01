package com.motorcorp.services.impl;

import com.motorcorp.application.enums.Role;
import com.motorcorp.application.modules.user.dao.UserDao;
import com.motorcorp.application.modules.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUserNameExitsInEmailOrPhone(username).orElse(null);
        if (user == null) throw new UsernameNotFoundException("User not found with username: " + username);
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), buildSimpleGrantedAuthorities(user.getRole()));
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }
}
