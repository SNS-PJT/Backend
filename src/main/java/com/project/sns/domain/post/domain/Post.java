package com.project.sns.domain.post.domain;

import com.project.sns.domain.user.domain.User;
import com.project.sns.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "post_id")
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Getter
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Getter
    private List<Image> images = new ArrayList<>();

    @Builder
    public Post(Long id, User user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public static Post createPost(User user, String content) {
        return Post.builder().
                   user(user).
                   content(content).
                   build();
    }

    public void modifyPost(String content) {
        this.content = content;
    }

    public void addImages(List<Image> images) {
        this.images.addAll(images);
    }

}
