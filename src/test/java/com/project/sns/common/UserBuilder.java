package com.project.sns.common;


import com.project.sns.domain.user.domain.User;
import com.project.sns.global.config.webmvc.AuthUser;

public class UserBuilder {


    public static User createUser(Long id, String nickname) {
        return MockUser.builder().
                       id(id)
                       .nickname(nickname)
                       .createMockUser()
                       .build();
    }

    public static AuthUser createAuthUser(Long id) {
        return new AuthUser(id);
    }
}
