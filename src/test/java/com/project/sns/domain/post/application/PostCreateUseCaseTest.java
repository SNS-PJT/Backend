package com.project.sns.domain.post.application;

import com.project.sns.domain.user.application.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostCreateUseCaseTest {

    @Mock
    UserRepository userRepository;


}
