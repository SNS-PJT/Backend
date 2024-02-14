package com.project.sns.domain.post.dto;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


@ExtendWith(MockitoExtension.class)
public class PostCreateRequestDtoBuilder {

    public static PostCreateRequestDto buildRequest(String content) throws IOException {
        PostCreateRequestDto requestDto = new PostCreateRequestDto();
        MockMultipartFile image = new MockMultipartFile("image-name", "test.png", "image/png",
                new FileInputStream("src/test/resources/sample-image.png"));

        List<MultipartFile> images = new ArrayList<>();
        images.add(image);

        requestDto.setImages(images);
        requestDto.setContent(content);

        return requestDto;
    }
}
