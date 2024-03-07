package com.project.sns.domain.keyword.application;

import com.project.sns.domain.keyword.application.repository.KeywordRepository;
import com.project.sns.domain.keyword.dto.KeywordResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordSearchUseCase {

    private KeywordRepository keywordRepository;

    public List<KeywordResponseDto> searchPopularKeywords() {
        return keywordRepository.findTop20Keyword()
                                .stream()
                                .map(KeywordResponseDto::from)
                                .collect(
                                        Collectors.toList());
    }
  
}
