package com.project.sns.domain.post.dto;

import com.project.sns.domain.post.domain.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
public class ImageResponseDto {

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    @Getter
    @Setter
    private Long imageId;
    @Getter
    @Setter
    private String imagePath;

    @Builder
    private ImageResponseDto(Long imageId, String imagePath) {
        this.imageId = imageId;
        this.imagePath = s3Url + imagePath;
    }

    public static ImageResponseDto from(Image image) {
        return ImageResponseDto.builder()
                               .imageId(image.getId())
                               .imagePath(image.getImagePath())
                               .build();
    }
}
