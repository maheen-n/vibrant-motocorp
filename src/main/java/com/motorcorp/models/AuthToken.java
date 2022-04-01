package com.motorcorp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthToken {
    private Long userId;

    private String token;

    private String name;

    private String role;

    private Long expireAt;

    public AuthToken(Long userId, String token, String name, String role, Long expireAt) {
        this.userId = userId;
        this.token = token;
        this.name = name;
        this.role = role;
        this.expireAt = expireAt;
    }
    public AuthToken(Long userId, String token, String name) {
        this.userId = userId;
        this.token = token;
        this.name = name;
    }
}
