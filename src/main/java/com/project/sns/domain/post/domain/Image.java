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
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

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
    private MultipartFile imageFile;

    @Builder
    private Image(Post post, String imagePath, MultipartFile imageFile) {
        this.post = post;
        this.imagePath = imagePath;
        this.imageFile = imageFile;
    }

    public static Image createImage(Post post, String imagePath, MultipartFile imageFile) {
        return Image.builder()
                    .post(post)
                    .imagePath(imagePath)
                    .imageFile(imageFile)
                    .build();
    }

    public static List<Image> createImages(Post post, List<String> imagePaths) {
        return imagePaths.stream()
                         .map(path -> {
                             return Image.builder()
                                         .post(post)
                                         .imagePath(path)
                                         .build();
                         })
                         .toList();
    }
}
