package com.project.sns.domain.post.dto;

import com.project.sns.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUserResponseDto {

    private static String IMAGE_DIRECTORY = "https://sns-pjt.s3.ap-northeast-2.amazonaws.com/";

    private Long id;
    private String nickname;
    private String profilePath;

    @Builder
    private PostUserResponseDto(Long id, String nickname, String profilePath) {
        this.id = id;
        this.nickname = nickname;
        this.profilePath = profilePath;
    }

    public static PostUserResponseDto from(User user) {
        return PostUserResponseDto.builder()
                                  .id(user.getId())
                                  .nickname(user.getNickname())
                                  .profilePath(IMAGE_DIRECTORY + user.getProfilePath())
                                  .build();
    }
}
