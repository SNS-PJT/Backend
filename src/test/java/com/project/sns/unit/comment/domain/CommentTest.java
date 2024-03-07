package com.project.sns.unit.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.project.sns.common.user.UserBuilder;
import com.project.sns.domain.comment.domain.Comment;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.exception.NotValidCommentContentException;
import com.project.sns.global.exception.user.NotValidUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class CommentTest {

    @DisplayName("200자 이하의 댓글을 생성할 수 있다.")
    @Test
    void createComment_LengthShorterThan200_Success() {
        // given
        String content = "댓글 내용";

        // when, then
        assertThatCode(() -> Comment.createComment(null, null, content)).doesNotThrowAnyException();

    }

    @DisplayName("200자가 초과되는 댓글을 생성할 수 없다")
    @Test
    void createComment_LengthOver200_ExceptionThrown() {
        // given
        String content = "댓글 내용".repeat(40);

        // when, then
        assertThatCode(() -> Comment.createComment(null, null, content)).isInstanceOf(
                                                                                NotValidCommentContentException.class)
                                                                        .extracting("statusCode")
                                                                        .isEqualTo(400);
    }

    @DisplayName("댓글 내용이 null이거나 빈 문자열이면 댓글을 생성할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void createComment_ContentIsNullOrEmpty_ExceptionThrown(String content) {
        // given

        // when, then
        assertThatCode(() -> Comment.createComment(null, null, content)).isInstanceOf(
                                                                                NotValidCommentContentException.class)
                                                                        .extracting("statusCode")
                                                                        .isEqualTo(400);
    }

    @DisplayName("댓글이 해당 유저가 작성한 댓글인지 확인한다.")
    @Test
    void isCommentedBy_isCommentedByCurrentUser_True() {

        // given
        User currentUser = UserBuilder.createUser(1L, "닉네임");
        Comment comment = Comment.createComment(null, currentUser, "내용");

        // when, then
        assertThat(comment.isCommentedBy(currentUser)).isTrue();
    }

    @DisplayName("댓글을 수정할 수 있다.")
    @Test
    void modifyComment_ValidContent_Success() {
        // given
        User author = UserBuilder.createUser(1L, "닉네임");
        Comment comment = Comment.createComment(null, author, "test content");

        // when
        String modifyContent = "modify_Content";
        comment.modifyContent(author, modifyContent);

        // then
        assertThat(comment.getContent()).isEqualTo(modifyContent);
    }

    @DisplayName("댓글을 수정하려는 유저가 작성자와 동일하지 않으면 댓글을 수정할 수 없다.")
    @Test
    void modifyComment_isNotCommentedByCurrentUser_ExceptionThrown() {
        // given
        User author = UserBuilder.createUser(1L, "닉네임1");
        User currentUser = UserBuilder.createUser(2L, "닉네임2");
        Comment comment = Comment.createComment(null, author, "test content");

        String modifyContent = "modify_Content";

        // when, then
        assertThat(author).isNotEqualTo(currentUser);
        assertThatCode(() -> comment.modifyContent(currentUser, modifyContent)).isInstanceOf(
                                                                                       NotValidUserException.class)
                                                                               .extracting(
                                                                                       "statusCode")
                                                                               .isEqualTo(403);
    }

    @DisplayName("댓글 내용이 null이거나 빈 문자열이면 댓글을 수정할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void modifyComment_ContentIsNullOrEmpty_ExceptionThrown(String content) {
        // given
        User author = UserBuilder.createUser(1L, "닉네임1");
        Comment comment = Comment.createComment(null, author, "test content");

        // when, then
        assertThatCode(() -> comment.modifyContent(author, content)).isInstanceOf(
                                                                            NotValidCommentContentException.class)
                                                                    .extracting("statusCode")
                                                                    .isEqualTo(400);
    }
}
