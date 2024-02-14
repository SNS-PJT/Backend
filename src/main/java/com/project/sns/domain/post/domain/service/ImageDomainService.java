package com.project.sns.domain.post.domain.service;

import com.project.sns.domain.post.domain.Image;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.global.exception.NotUploadFileException;
import com.project.sns.infra.aws.S3Uploader;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageDomainService {

    private final S3Uploader s3Uploader;
    private final String DIRECTORY = "post/image/";

    public List<Image> createImages(List<MultipartFile> images, Post savedPost) {
        if (images == null || images.isEmpty()) {
            throw new NotUploadFileException("파일을 하나 이상 업로드 해주세요.");
        }

        return images.stream()
                     .map(image -> Image.createImage(savedPost,
                             s3Uploader.makeFileName(DIRECTORY,
                                     image), image))
                     .collect(Collectors.toList());
    }
}
