package com.project.sns.unit.comment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.sns.common.dto.RequestDtoTest;
import com.project.sns.domain.comment.dto.CommentAddRequestDto;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CommentAddRequestDtoTest extends RequestDtoTest {


    @DisplayName("댓글 등록 요청을 할 수 있다.")
    @Test
    void CommentAddRequestDto_ValidContent_Success() {
        // given
        final Long postId = 1L;
        final String content = "test content";

        CommentAddRequestDto commentAddRequestDto = new CommentAddRequestDto(postId, content);

        // when
        Set<ConstraintViolation<CommentAddRequestDto>> violations = validator.validate(
                commentAddRequestDto);

        // then
        assertThat(violations).isNullOrEmpty();
    }

    @DisplayName("댓글 등록 요청 시 게시글 내용이 반드시 있어야 한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void CommentAddRequestDto_CotentIsNullOrBlank_Fail(String content) {
        // given
        final Long postId = 1L;
        CommentAddRequestDto commentAddRequestDto = new CommentAddRequestDto(postId, content);

        // when
        Set<ConstraintViolation<CommentAddRequestDto>> violations = validator.validate(
                commentAddRequestDto);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("댓글 등록 요청 시 게시글 번호가 반드시 있어야 한다.")
    @Test
    void CommentAddRequestDto_PostIdIsNull_Fail() {
        // given
        final Long postId = null;
        final String content = "test content";

        CommentAddRequestDto commentAddRequestDto = new CommentAddRequestDto(postId, content);

        // when
        Set<ConstraintViolation<CommentAddRequestDto>> violations = validator.validate(
                commentAddRequestDto);

        // then
        assertThat(violations).isNotEmpty();
    }

}