package com.project.sns.domain.keyword.infra.database;

import com.project.sns.domain.keyword.application.repository.KeywordRepository;
import com.project.sns.domain.keyword.domain.Keyword;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KeywordRepositoryImpl implements KeywordRepository {

    private JPAKeywordRepository jpaKeywordRepository;

    @Override
    public Keyword save(Keyword keyword) {
        return jpaKeywordRepository.save(keyword);
    }

    @Override
    public Optional<Keyword> findByContent(String content) {
        return jpaKeywordRepository.findByContent(content);
    }

    @Override
    public List<Keyword> findKeywordByContentIn(List<String> contents) {
        return jpaKeywordRepository.findKeywordByContentIn(contents);
    }

    @Override
    public List<Keyword> findTop20Keyword() {
        return jpaKeywordRepository.findTop20ByOrderByKeywordCountDesc();
    }

}
