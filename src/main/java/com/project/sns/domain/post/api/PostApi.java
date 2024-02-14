package com.project.sns.domain.post.api;

import com.project.sns.domain.post.application.PostSearchUseCase;
import com.project.sns.domain.post.application.PostUploadUseCase;
import com.project.sns.domain.post.dto.PostCreateRequestDto;
import com.project.sns.domain.post.dto.PostResponseDto;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.global.dto.HttpResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostApi {

    private final PostUploadUseCase postCreateUseCase;
    private final PostSearchUseCase postSearchUseCase;


    @PostMapping
    public ResponseEntity<?> uploadPost(AuthUser authUser,
            @RequestBody @Valid PostCreateRequestDto postCreateRequestDto) {
        postCreateUseCase.uploadPost(authUser, postCreateRequestDto);
        return HttpResponseDto.ok(HttpStatus.CREATED, "게시글을 업로드했습니다.");
    }

    @GetMapping("/my-posts")
    public ResponseEntity<?> searchMyPosts(AuthUser authUser) {
        List<PostResponseDto> posts = postSearchUseCase.searchMyPosts(authUser);
        return HttpResponseDto.okWithData(HttpStatus.OK, "내 게시글을 조회했습니다.", posts);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> searchPostsByUserId(@PathVariable("user_id") @Min(1) Long userId) {
        List<PostResponseDto> posts = postSearchUseCase.searchPostsByUserId(userId);
        return HttpResponseDto.okWithData(HttpStatus.OK, "내 게시글을 조회했습니다.", posts);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByKeyword(
            @RequestParam(name = "keyword", required = true) String keyword) {
        List<PostResponseDto> posts = postSearchUseCase.searchPostByKeyword(keyword);
        return HttpResponseDto.okWithData(HttpStatus.OK, "내 게시글을 조회했습니다.", posts);
    }
}
