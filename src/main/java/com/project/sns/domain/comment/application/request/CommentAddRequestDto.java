package com.project.sns.domain.comment.application.request;

public record CommentAddRequestDto(Long userId, Long postId, String content) {

}
