package com.project.sns.domain.post.dto;

import com.project.sns.domain.post.domain.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageResponseDto {

    private static String IMAGE_DIRECTORY = "https://sns-pjt.s3.ap-northeast-2.amazonaws.com/";

    private Long imageId;
    private String imagePath;

    @Builder
    private ImageResponseDto(Long imageId, String imagePath) {
        this.imageId = imageId;
        this.imagePath = imagePath;
    }

    public static ImageResponseDto from(Image image) {
        return ImageResponseDto.builder()
                               .imageId(image.getId())
                               .imagePath(IMAGE_DIRECTORY + image.getImagePath())
                               .build();
    }
}
