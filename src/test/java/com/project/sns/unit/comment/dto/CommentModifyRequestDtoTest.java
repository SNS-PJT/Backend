package com.project.sns.unit.comment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.sns.common.dto.RequestDtoTest;
import com.project.sns.domain.comment.dto.CommentModifyRequestDto;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CommentModifyRequestDtoTest extends RequestDtoTest {

    @DisplayName("댓글 수정 요청을 할 수 있다.")
    @Test
    void CommentModifyRequestDto_ValidContent_Success() {
        // given
        final Long commentId = 1L;
        final String content = "test content";

        CommentModifyRequestDto commentModifyRequestDto = new CommentModifyRequestDto(commentId,
                content);

        // when
        Set<ConstraintViolation<CommentModifyRequestDto>> violations = validator.validate(
                commentModifyRequestDto);

        // then
        assertThat(violations).isNullOrEmpty();
    }

    @DisplayName("댓글 번호가 없으면 댓글 수정 요청을 할 수 없다.")
    @Test
    void CommentModifyRequestDto_CommentIdIsNull_Success() {
        // given
        final Long commentId = null;
        final String content = "test content";

        CommentModifyRequestDto commentModifyRequestDto = new CommentModifyRequestDto(commentId,
                content);

        // when
        Set<ConstraintViolation<CommentModifyRequestDto>> violations = validator.validate(
                commentModifyRequestDto);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("댓글 번호가 없으면 댓글 수정 요청을 할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void CommentModifyRequestDto_ContentIsNullOrBlank_Fail(String content) {
        // given
        final Long commentId = 1L;

        CommentModifyRequestDto commentModifyRequestDto = new CommentModifyRequestDto(commentId,
                content);

        // when
        Set<ConstraintViolation<CommentModifyRequestDto>> violations = validator.validate(
                commentModifyRequestDto);

        // then
        assertThat(violations).isNotEmpty();
    }


    @DisplayName("200자 제한을 넘으면 댓글 수정 요청을 할 수 없다.")
    @Test
    void CommentModifyRequestDto_LengthOver200_Success() {
        // given
        final Long commentId = 1L;
        final String content = "Hi".repeat(100) + "1";

        CommentModifyRequestDto commentModifyRequestDto = new CommentModifyRequestDto(commentId,
                content);

        // when
        Set<ConstraintViolation<CommentModifyRequestDto>> violations = validator.validate(
                commentModifyRequestDto);

        // then
        assertThat(content.length()).isEqualTo(201);
        assertThat(violations).isNotEmpty();
    }

}
