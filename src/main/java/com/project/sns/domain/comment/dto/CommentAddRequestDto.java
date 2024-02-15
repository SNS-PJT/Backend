package com.project.sns.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class CommentAddRequestDto {

    public CommentAddRequestDto(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    @NotNull
    private Long postId;

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Length(max = 200)
    private String content;


}
