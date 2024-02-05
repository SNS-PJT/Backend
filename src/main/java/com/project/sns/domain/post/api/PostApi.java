package com.project.sns.domain.post.api;

import com.project.sns.domain.post.application.PostCreateUseCase;
import com.project.sns.domain.post.dto.PostCreateRequestDto;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.global.dto.HttpResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostApi {

    private final PostCreateUseCase postCreateUseCase;

    @PostMapping
    public ResponseEntity<?> uploadPost(AuthUser authUser,
            @ModelAttribute @Valid PostCreateRequestDto postCreateRequestDto) {
        postCreateUseCase.createPost(authUser, postCreateRequestDto);
        return HttpResponseDto.ok(HttpStatus.CREATED, "게시글을 업로드했습니다.");
    }
}
