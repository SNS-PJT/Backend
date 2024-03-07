package com.project.sns.domain.keyword.dto;

import com.project.sns.domain.keyword.domain.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class KeywordResponseDto {

    private Long id;
    private String content;

    @Builder
    private KeywordResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static KeywordResponseDto from(Keyword keyword) {
        return KeywordResponseDto.builder()
                                 .id(keyword.getId())
                                 .content(keyword.getContent())
                                 .build();
    }
}
