package com.project.sns.domain.post.application;

import com.amazonaws.services.kms.model.NotFoundException;
import com.project.sns.domain.post.application.repository.ImageRepository;
import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Image;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.post.dto.PostCreateRequestDto;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.global.exception.NotUploadFileException;
import com.project.sns.infra.aws.S3Uploader;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCreateUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    private final String DIRECTORY = "post/image/";

    public void createPost(AuthUser authUser, PostCreateRequestDto postCreateDto) {
        User user = userRepository.findById(authUser.getUserId())
                                  .orElseThrow(
                                          () -> new NotFoundException("아이디에 해당하는 사용자를 찾을 수 없습니다."));

        Post newPost = Post.createPost(user, postCreateDto.getContent());
        Post savedPost = postRepository.save(newPost);

        List<Image> savedImages = saveImages(postCreateDto.getImages(), savedPost);

        if (savedImages != null) {
            savedPost.addImages(savedImages);
            savedImages.forEach(
                    image -> s3Uploader.upload(image.getImagePath(), image.getImageFile()));
        }

    }

    private List<Image> saveImages(List<MultipartFile> images, Post savedPost) {
        if (images == null || images.isEmpty()) {
            throw new NotUploadFileException("파일을 하나 이상 업로드 해주세요.");
        }

        List<Image> newImages = images.stream()
                                      .map(image -> Image.createImage(savedPost,
                                              s3Uploader.makeFileName(DIRECTORY,
                                                      image), image))
                                      .collect(Collectors.toList());

        return imageRepository.savedAll(newImages);
    }


}
