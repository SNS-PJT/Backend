package com.project.sns.domain.keyword.application;

import com.project.sns.domain.keyword.application.repository.KeywordRepository;
import com.project.sns.domain.keyword.domain.Keyword;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KeywordAddUseCase {

    private final KeywordRepository keywordRepository;

    public List<Keyword> findOrCreateKeyword(List<String> contents) {
        if (contents == null || contents.isEmpty()) {
            return Collections.emptyList();
        }

        return contents.stream()
                       .map(this::getKeywordOrCreateKeyword)
                       .collect(Collectors.toList());
    }

    private Keyword getKeywordOrCreateKeyword(String content) {
        Keyword savedKeyword = keywordRepository.findByContent(content)
                                                .orElse(null);

        if (savedKeyword == null) {
            return keywordRepository.save(new Keyword(content));
        }
        
        savedKeyword.addKeywordCount();

        return savedKeyword;
    }
}
