package com.project.sns.domain.keyword.api;

import com.project.sns.domain.keyword.application.KeywordSearchUseCase;
import com.project.sns.domain.keyword.dto.KeywordResponseDto;
import com.project.sns.global.dto.HttpResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordApi {

    private KeywordSearchUseCase keywordSearchUseCase;

    @GetMapping
    public ResponseEntity<?> searchPopularKeywords() {
        List<KeywordResponseDto> keywords = keywordSearchUseCase.searchPopularKeywords();
        return HttpResponseDto.okWithData(HttpStatus.OK, "상위 20개의 키워드를 검색했습니다.", keywords);
    }

}
