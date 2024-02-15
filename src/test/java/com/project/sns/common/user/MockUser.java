package com.project.sns.common.user;

import com.project.sns.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockUser {

    private Long id;

    private String nickname;

    @Builder(buildMethodName = "createMockUser")
    public MockUser(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public User build() {
        return User.builder()
                   .id(id)
                   .nickname(nickname)
                   .build();
    }


}
