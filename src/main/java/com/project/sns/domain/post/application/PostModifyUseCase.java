package com.project.sns.domain.post.application;

import com.project.sns.domain.post.application.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostModifyUseCase {
    
    private final PostRepository postRepository;

}
