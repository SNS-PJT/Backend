package com.project.sns.unit.comment.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class CommentModifyRequestBody {

    private Long userId;
    private Long commentId;
    private String content;

    public CommentModifyRequestBody(Long userId, Long commentId, String content) {
        this.userId = userId;
        this.commentId = commentId;
        this.content = content;
    }
}
