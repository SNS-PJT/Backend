package com.project.sns.unit.comment.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class CommentAddRequestBody {

    private Long userId;
    private Long postId;
    private String content;

    public CommentAddRequestBody(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }
}
