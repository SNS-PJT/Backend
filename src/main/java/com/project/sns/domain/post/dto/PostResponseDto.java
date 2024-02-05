package com.project.sns.domain.post.dto;

import com.project.sns.domain.post.domain.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private PostUserResponseDto user;
    private String content;
    private List<ImageResponseDto> images = new ArrayList<>();

    @Builder
    private PostResponseDto(Long id, PostUserResponseDto user, String content,
            List<ImageResponseDto> images) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.images = images;
    }

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                              .id(post.getId())
                              .user(PostUserResponseDto.from(post.getUser()))
                              .content(post.getContent())
                              .images(post.getImages()
                                          .stream()
                                          .map(ImageResponseDto::from)
                                          .toList())
                              .build();
    }
}
