package com.project.sns.domain.post.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class PostCreateRequestDtoTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeEach
    void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    private PostCreateRequestDto buildRequest() throws IOException {
        PostCreateRequestDto requestDto = new PostCreateRequestDto();
        MockMultipartFile image = new MockMultipartFile("image-name", "test.png", "image/png",
                new FileInputStream("src/test/resources/sample-image.png"));

        List<MultipartFile> images = new ArrayList<>();
        images.add(image);

        requestDto.setImages(images);

        return requestDto;
    }

    @DisplayName("게시글 업로드 요청 시 게시글 내용이 반드시 있어야 한다.")
    @Test
    void blackContentThrowsException() throws IOException {
        // given
        String content = "";
        List<MultipartFile> images = new ArrayList<>();
        images.add(new MockMultipartFile("image-name", "test.png", "image/png",
                new FileInputStream("src/test/resources/sample-image.png")));

        PostCreateRequestDto createRequestDto = new PostCreateRequestDto(content, images);

        // when
        Set<ConstraintViolation<PostCreateRequestDto>> violations = validator.validate(
                createRequestDto);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("게시글 업로드 요청 시 허용된 타입의 이미지 파일만 업로드할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"text/plain", "application/pdf"})
    void NotAllowedContentTypeThrowsException(String contentType) throws IOException {
        // given
        String content = "";
        List<MultipartFile> images = new ArrayList<>();
        images.add(new MockMultipartFile("image-name", "test.png", contentType,
                new FileInputStream("src/test/resources/sample-image.png")));

        PostCreateRequestDto createRequestDto = new PostCreateRequestDto(content, images);

        // when
        Set<ConstraintViolation<PostCreateRequestDto>> violations = validator.validate(
                createRequestDto);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("게시글 업로드 요청시 정상적인 값이 오면 성공한다.")
    @Test
    void success() throws IOException {
        String content = "내용1";
        List<MultipartFile> images = new ArrayList<>();
        images.add(new MockMultipartFile("image-name", "test.png", "image/png",
                new FileInputStream("src/test/resources/sample-image.png")));

        PostCreateRequestDto createRequestDto = new PostCreateRequestDto(content, images);

        // when
        Set<ConstraintViolation<PostCreateRequestDto>> violations = validator.validate(
                createRequestDto);

        // then
        assertThat(violations).isEmpty();
    }


}
