package com.project.sns.domain.comment.api;

import com.project.sns.domain.comment.application.CommentAddUseCase;
import com.project.sns.domain.comment.dto.CommentAddRequestDto;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.global.dto.HttpResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentAddUseCase commentAddUseCase;

    @PostMapping
    public ResponseEntity<?> addComment(AuthUser loginUser,
            @RequestBody @Valid CommentAddRequestDto requestDto) {
        commentAddUseCase.addComment(loginUser, requestDto);
        return HttpResponseDto.ok(HttpStatus.CREATED, "댓글을 등록했습니다.");
    }

}
