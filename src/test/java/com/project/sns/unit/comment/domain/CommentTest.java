package com.project.sns.unit.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.project.sns.common.UserBuilder;
import com.project.sns.domain.comment.domain.Comment;
import com.project.sns.domain.user.domain.User;
import com.project.sns.global.exception.NotValidCommentContentException;
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

}
