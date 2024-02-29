package com.project.sns.domain.post.dto;

import com.project.sns.domain.post.domain.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ImageResponseDto {

    private Long imageId;
    private String imagePath;

    private static final String s3Url = "https://sns-pjt.s3.ap-northeast-2.amazonaws.com/";

    @Builder
    private ImageResponseDto(Long imageId, String imagePath) {
        this.imageId = imageId;
        this.imagePath = imagePath;
    }

    public static ImageResponseDto from(Image image) {
        return ImageResponseDto.builder()
                               .imageId(image.getId())
                               .imagePath(s3Url + image.getImagePath())
                               .build();
    }
}
