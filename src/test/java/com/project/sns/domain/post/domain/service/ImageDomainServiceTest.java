package com.project.sns.domain.post.domain.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.project.sns.domain.post.application.repository.ImageRepository;
import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Image;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.post.dto.PostCreateRequestDto;
import com.project.sns.domain.post.dto.PostCreateRequestDtoBuilder;
import com.project.sns.infra.aws.S3Uploader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImageDomainServiceTest {

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private S3Uploader s3Uploader;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private ImageDomainService imageDomainService;

    private List<Image> images;

    private Post post;

    @Test
    void 이미지_생성() throws IOException {
        // given
        String content = "내용1";
        PostCreateRequestDto requestDto = PostCreateRequestDtoBuilder.buildRequest(content);

        // when
        List<Image> images = imageDomainService.createImages(requestDto.getImages(), Post.builder()
                                                                                         .content(
                                                                                                 content)
                                                                                         .build());
        assertThat(images).isNotNull();
    }
}