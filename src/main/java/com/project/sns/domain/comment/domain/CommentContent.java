package com.project.sns.domain.comment.domain;

import com.project.sns.global.exception.NotValidCommentContentException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentContent {

    private static final int MAX_COMMNET_LENGTH = 200;

    @Column(nullable = false, length = 200, columnDefinition = "TEXT")
    @Getter
    private String content;


    public CommentContent(String content) {
        if (isNotValidContent(content)) {
            throw new NotValidCommentContentException("댓글 양식을 준수하지 않는 댓글입니다.");
        }
        this.content = content;
    }

    private boolean isNotValidContent(String content) {
        return Objects.isNull(content) || content.isBlank()
                || content.length() >= MAX_COMMNET_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentContent that = (CommentContent) o;
        return Objects.equals(content, that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
