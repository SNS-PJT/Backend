package com.project.sns.domain.comment.application;

import com.project.sns.domain.comment.application.repository.CommentRepository;
import com.project.sns.domain.comment.domain.Comment;
import com.project.sns.domain.comment.dto.CommentModifyRequestDto;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentModifyUseCase {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void modifyComment(AuthUser authUser, CommentModifyRequestDto modifyRequestDto) {
        Comment savedComment = commentRepository.findById(modifyRequestDto.getCommentId())
                                                .orElseThrow(() -> new NotFoundException(
                                                        "댓글 번호에 해당하는 댓글을 찾을 수 없습니다."));

        User loginUser = userRepository.findById(authUser.getUserId())
                                       .orElseThrow(() -> new NotFoundException(
                                               "아이디에 해당하는 사용자를 찾을 수 없습니다."));

        savedComment.modifyContent(loginUser, modifyRequestDto.getContent());
    }
}
