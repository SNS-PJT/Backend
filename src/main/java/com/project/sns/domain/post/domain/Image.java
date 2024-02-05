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
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "post_id")
    private Post post;

    @Column(nullable = false, length = 200)
    private String image_path;

    @Builder
    private Image(Post post, String image_path) {
        this.post = post;
        this.image_path = image_path;
    }

    public static List<Image> createImages(Post post, List<String> image_paths) {
        return image_paths.stream()
                          .map(path -> {
                              return Image.builder()
                                          .post(post)
                                          .image_path(path)
                                          .build();
                          })
                          .toList();
    }
}
