package com.project.sns.domain.post.application;

import com.project.sns.domain.post.application.repository.ImageRepository;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.infra.aws.S3Uploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostCreateUseCaseTest {

    @Mock
    UserRepository userRepository;
    @Mock
    ImageRepository imageRepository;
    @Mock
    S3Uploader s3Uploader;

    @Test
    void 게시글_등록() {
        
    }

}
