package com.project.sns.unit.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.project.sns.common.UserBuilder;
import com.project.sns.domain.comment.application.CommentAddUseCase;
import com.project.sns.domain.comment.application.repository.CommentRepository;
import com.project.sns.domain.comment.application.request.CommentAddRequestDto;
import com.project.sns.domain.comment.domain.Comment;
import com.project.sns.domain.post.application.repository.PostRepository;
import com.project.sns.domain.post.domain.Post;
import com.project.sns.domain.user.application.repository.UserRepository;
import com.project.sns.domain.user.domain.User;
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
public class CommentAddUseCaseTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentAddUseCase commentAddUseCase;


    @DisplayName("게시글에 댓글을 정상적으로 등록한다")
    @Test
    void addComment_ValidContent_Success() {
        // given
        final String content = "댓글 내용";

        final User user = UserBuilder.createUser(1L, "testUser");
        final Post post = Post.builder()
                              .id(1L)
                              .user(user)
                              .build();

        final Comment comment = Comment.createComment(post, user, content);

        final CommentAddRequestDto request = new CommentAddRequestDto(1L, 1L, "댓글 내용");

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        // when, then
        assertThat(comment.getAuthorName()).isEqualTo(user.getNickname());

        assertThatCode(() -> commentAddUseCase.addComment(request)).doesNotThrowAnyException();

        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(userRepository, times(1)).findById(anyLong());
        verify(postRepository, times(1)).findById(anyLong());
    }

    @DisplayName("댓글 내용이 null이거나 비어있는 문자열이면 댓글을 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void addComment_ContentIsNullOrEmpty_ExceptionThrown(String content) {
        // given
        final User user = UserBuilder.createUser(1L, "testUser");
        final Post post = Post.builder()
                              .id(1L)
                              .build();

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        final CommentAddRequestDto request = new CommentAddRequestDto(user.getId(), post.getId(),
                content);

        // when, then
        assertThatCode(() -> commentAddUseCase.addComment(request)).isInstanceOf(
                                                                           NotValidCommentContentException.class)
                                                                   .extracting("statusCode")
                                                                   .isEqualTo(400);

        verify(commentRepository, times(0)).save(any(Comment.class));
        verify(userRepository, times(1)).findById(anyLong());
        verify(postRepository, times(1)).findById(anyLong());

    }

    @DisplayName("존재하지 않는 게시물에는 댓글을 작성할 수 없다")
    @Test
    void addComment_NotValidPost_ExceptionThrown() {
        // given
        final User user = UserBuilder.createUser(1L, "testUser");
        final Long postId = -1L;
        final String content = "test comment";

        final CommentAddRequestDto request = new CommentAddRequestDto(user.getId(), postId,
                content);

        // when, then
        assertThatCode(() -> commentAddUseCase.addComment(request)).isInstanceOf(
                                                                           NotFoundException.class)
                                                                   .extracting("statusCode")
                                                                   .isEqualTo(404);

        verify(postRepository, times(1)).findById(anyLong());

    }

    @DisplayName("존재하지 않는 사용자는 댓글을 작성할 수 없다.")
    @Test
    void addComment_NotValidUser_ExceptionThrown() {
        // given
        final User user = UserBuilder.createUser(-1L, "testUser");
        final Post post = Post.builder()
                              .id(1L)
                              .build();
        final String content = "test comment";

        final CommentAddRequestDto request = new CommentAddRequestDto(user.getId(), post.getId(),
                content);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when, then
        assertThatCode(() -> commentAddUseCase.addComment(request)).isInstanceOf(
                                                                           NotFoundException.class)
                                                                   .extracting("statusCode")
                                                                   .isEqualTo(404);

        verify(postRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());

    }
}
