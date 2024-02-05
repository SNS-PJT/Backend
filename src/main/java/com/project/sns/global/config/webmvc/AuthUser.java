package com.project.sns.global.config.webmvc;

import lombok.Getter;

@Getter
public class AuthUser {

    private final Long userId;

    public AuthUser(Long userId) {
        this.userId = userId;
    }
}
