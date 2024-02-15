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
public class CommentModifyRequestDto {

    @NotNull
    private Long id;

    @NotBlank
    @Length(max = 200)
    private String content;

    public CommentModifyRequestDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
