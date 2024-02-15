package com.project.sns.domain.comment.domain;

import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.entity.BaseEntity;
import com.project.sns.global.exception.NotValidUserException;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comment")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Getter
    private Post post;

    @Embedded
    private CommentContent content;

    @Builder
    private Comment(Long id, User user, Post post, CommentContent content) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public static Comment createComment(Post post, User user, String content) {
        return Comment.builder()
                      .post(post)
                      .user(user)
                      .content(new CommentContent(content))
                      .build();
    }

    public String getProfileImagePath() {
        return user.getProfilePath();
    }

    public String getAuthorName() {
        return user.getNickname();
    }

    public String getContent() {
        return this.content.getContent();
    }

    public boolean isCommentedBy(User user) {
        return this.user.equals(user);
    }

    public void modifyContent(User user, String content) {
        if (!isCommentedBy(user)) {
            throw new NotValidUserException("댓글의 작성자와 동일하지 않습니다.");
        }
        this.content.setContent(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
