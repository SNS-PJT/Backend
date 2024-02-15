package com.project.sns.unit.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.project.sns.common.user.UserBuilder;
import com.project.sns.domain.comment.application.CommentModifyUseCase;
import com.project.sns.domain.comment.application.repository.CommentRepository;
import com.project.sns.domain.comment.domain.Comment;
import com.project.sns.domain.comment.dto.CommentModifyRequestDto;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.config.webmvc.AuthUser;
import com.project.sns.global.exception.NotFoundException;
import com.project.sns.global.exception.NotValidCommentContentException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentModifyUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentModifyUseCase commentModifyUseCase;


    @Test
    @DisplayName("댓글을 수정할 수 있다.")
    void modifyComment_ValidContent_Success() {
        // given
        Long commentId = 1L;
        String modifyContent = "modify_content";

        final User user = UserBuilder.createUser(1L, "testUser");
        final AuthUser loginUser = UserBuilder.createAuthUser(user.getId());

        final Comment savedComment = Comment.createComment(null, user, "test content");
        final CommentModifyRequestDto modifyRequestDto = new CommentModifyRequestDto(1L,
                modifyContent);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(savedComment));
        given(userRepository.findById(loginUser.getUserId())).willReturn(Optional.of(user));

        // when
        commentModifyUseCase.modifyComment(loginUser, modifyRequestDto);

        // then
        assertThat(user.getId()).isEqualTo(loginUser.getUserId());
        assertThat(savedComment.getContent()).isEqualTo(modifyContent);

        verify(commentRepository, times(1)).findById(commentId);
        verify(userRepository, times(1)).findById(loginUser.getUserId());
    }

    @Test
    @DisplayName("존재하지 않는 댓글은 수정할 수 없다.")
    void modifyComment_NotExistCommentId_ExceptionThrown() {
        // given
        Long requestedCommentId = -1L;
        String modifyContent = "modify_content";

        final User user = UserBuilder.createUser(1L, "testUser");
        final AuthUser loginUser = UserBuilder.createAuthUser(user.getId());

        final CommentModifyRequestDto modifyRequestDto = new CommentModifyRequestDto(
                requestedCommentId,
                modifyContent);

        given(commentRepository.findById(modifyRequestDto.getId())).willReturn(Optional.empty());

        // when, then
        assertThatCode(
                () -> commentModifyUseCase.modifyComment(loginUser, modifyRequestDto)).isInstanceOf(
                                                                                              NotFoundException.class)
                                                                                      .extracting(
                                                                                              "statusCode")
                                                                                      .isEqualTo(
                                                                                              404);

        verify(commentRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).findById(anyLong());
    }

    @Test
    @DisplayName("존재하지 않는 사용자는 댓글을 수정할 수 없다.")
    void modifyComment_NotExistUser_ExceptionThrown() {
        // given

        final User user = UserBuilder.createUser(1L, "testUser");
        final AuthUser loginUser = UserBuilder.createAuthUser(user.getId());

        final CommentModifyRequestDto modifyRequestDto = new CommentModifyRequestDto(
                1L,
                "modify_content");
        final Comment comment = Comment.builder()
                                       .build();

        given(commentRepository.findById(modifyRequestDto.getId())).willReturn(
                Optional.of(comment));
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatCode(
                () -> commentModifyUseCase.modifyComment(loginUser, modifyRequestDto)).isInstanceOf(
                                                                                              NotFoundException.class)
                                                                                      .extracting(
                                                                                              "statusCode")
                                                                                      .isEqualTo(
                                                                                              404);

        verify(commentRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @DisplayName("댓글 내용이 null이거나 빈 문자열이면 댓글을 수정할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullAndEmptySource
    void modifyComment_ContentIsNullOrEmpty_ExceptionOccur(String content) {

        // given
        final User user = UserBuilder.createUser(1L, "testUser");
        final AuthUser loginUser = UserBuilder.createAuthUser(user.getId());

        final Comment comment = Comment.createComment(null, user, "test content");

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        final CommentModifyRequestDto modifyRequestDto = new CommentModifyRequestDto(
                1L,
                content);

        // when, then
        assertThatCode(() ->
                commentModifyUseCase.modifyComment(loginUser,
                        modifyRequestDto)).isInstanceOf(NotValidCommentContentException.class)
                                          .extracting("statusCode")
                                          .isEqualTo(400);

        verify(commentRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @DisplayName("댓글 내용이 200자 이상이면 댓글을 수정할 수 없다.")
    @Test
    void modifyComment_LengthOver200_ExceptionOccur() {

        // given

        final User user = UserBuilder.createUser(1L, "testUser");
        final AuthUser loginUser = UserBuilder.createAuthUser(user.getId());

        final Comment comment = Comment.createComment(null, user, "test content");

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        final String modifyContent = "modify".repeat(50);
        final CommentModifyRequestDto modifyRequestDto = new CommentModifyRequestDto(
                1L,
                modifyContent);

        // when, then
        assertThatCode(() ->
                commentModifyUseCase.modifyComment(loginUser,
                        modifyRequestDto)).isInstanceOf(NotValidCommentContentException.class)
                                          .extracting("statusCode")
                                          .isEqualTo(400);

        verify(commentRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
    }


}
