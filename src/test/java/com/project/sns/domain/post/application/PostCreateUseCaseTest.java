package com.project.sns.domain.post.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project.sns.domain.post.application.repository.ImageRepository;
import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Image;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.post.domain.service.ImageDomainService;
import com.project.sns.domain.post.dto.PostCreateRequestDto;
import com.project.sns.domain.post.dto.PostCreateRequestDtoBuilder;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.Gender;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.infra.aws.S3Uploader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostCreateUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageDomainService imageService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private S3Uploader s3Uploader;

    @InjectMocks
    private PostUploadUseCase postCreateUseCase;

    private User user;
    private List<Image> images;
    private Post post;


    void set_up() {
        user = User.createUser("oauthId", "nickname", "email", LocalDate.now(), Gender.F,
                "profilePath", "profileImage");
        images = new ArrayList<>();
        post = Post.createPost(user, "내용1");
    }

    @Test
    void 게시글_등록_성공() throws IOException {

        set_up();

        AuthUser authUser = new AuthUser(user.getId());
        PostCreateRequestDto requestDto = PostCreateRequestDtoBuilder.buildRequest(
                post.getContent());

        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(postRepository.save(any())).willReturn(post);
        given(imageService.createImages(any(), any())).willReturn(images);

        // when
        postCreateUseCase.uploadPost(authUser, requestDto);


    }

}
