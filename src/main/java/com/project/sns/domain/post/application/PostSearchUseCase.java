package com.project.sns.domain.post.application;

import com.amazonaws.services.kms.model.NotFoundException;
import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.post.dto.PostResponseDto;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.config.webmvc.AuthUser;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class PostSearchUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponseDto> searchMyPosts(AuthUser authUser) {
        User loginUser = userRepository.findById(authUser.getUserId())
                                       .orElseThrow(
                                               () -> new NotFoundException(
                                                       "아이디에 해당하는 사용자를 찾을 수 없습니다."));

        // 성능 체크
        List<Post> posts = postRepository.findAllByUser(loginUser);

        return posts.stream()
                    .map(PostResponseDto::from)
                    .toList();
    }
}
