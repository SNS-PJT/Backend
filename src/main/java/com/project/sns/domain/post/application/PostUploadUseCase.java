package com.project.sns.domain.post.application;

import com.amazonaws.services.kms.model.NotFoundException;
import com.project.sns.domain.post.application.repository.ImageRepository;
import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Image;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.post.domain.service.ImageDomainService;
import com.project.sns.domain.post.dto.PostCreateRequestDto;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.infra.aws.S3Uploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostUploadUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageDomainService imageService;
    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    public void uploadPost(AuthUser authUser, PostCreateRequestDto postCreateDto) {
        User user = userRepository.findById(authUser.getUserId())
                                  .orElseThrow(
                                          () -> new NotFoundException("아이디에 해당하는 사용자를 찾을 수 없습니다."));

        Post newPost = Post.createPost(user, postCreateDto.getContent());
        Post savedPost = postRepository.save(newPost);

        List<Image> newImages = imageService.createImages(postCreateDto.getImages(), savedPost);
        List<Image> savedImages = imageRepository.savedAll(newImages);

        savedPost.addImages(savedImages);
        savedImages.forEach(
                image -> s3Uploader.upload(image.getImagePath(), image.getImageFile()));

    }


}
