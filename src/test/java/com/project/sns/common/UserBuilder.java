package com.project.sns.common;


import com.project.sns.domain.user.domain.User;

public class UserBuilder {


    public static User createUser(Long id, String nickname) {
        return MockUser.builder().
                       id(id)
                       .nickname(nickname)
                       .createMockUser()
                       .build();
    }
}
