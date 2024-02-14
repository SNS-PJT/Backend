package com.project.sns.domain.comment.application;

import com.project.sns.domain.comment.application.repository.CommentRepository;
import com.project.sns.domain.comment.application.request.CommentAddRequestDto;
import com.project.sns.domain.comment.domain.Comment;
import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentAddUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public void addComment(CommentAddRequestDto request) {
        Post savedPost = postRepository.findById(request.postId())
                                       .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        User loginUser = userRepository.findById(request.userId())
                                       .orElseThrow(() -> new NotFoundException(
                                               "아이디에 해당하는 사용자를 찾을 수 없습니다."));

        Comment newComment = Comment.createComment(savedPost, loginUser, request.content());

        commentRepository.save(newComment);
    }
}
