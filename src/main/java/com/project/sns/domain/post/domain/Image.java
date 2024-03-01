package com.project.sns.domain.post.domain;

import com.project.sns.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    private static final String s3Url = "https://sns-pjt.s3.ap-northeast-2.amazonaws.com/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "post_id")
    private Post post;

    @Column(nullable = false, length = 200, name = "image_path")
    @Getter
    private String imagePath;

    @Transient
    @Getter
    private String imageFileName;

    @Transient
    @Getter
    private MultipartFile imageFile;

    @Builder
    private Image(Post post, String imagePath, String imageFileName, MultipartFile imageFile) {
        this.post = post;
        this.imagePath = imagePath;
        this.imageFileName = imageFileName;
        this.imageFile = imageFile;
    }

    public static Image createImage(Post post, String imageFileName, MultipartFile imageFile) {
        return Image.builder()
                    .post(post)
                    .imagePath(s3Url + imageFileName)
                    .imageFileName(imageFileName)
                    .imageFile(imageFile)
                    .build();
    }

}
