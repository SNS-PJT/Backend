package com.project.sns.domain.keyword.application.repository;

import com.project.sns.domain.keyword.domain.Keyword;
import java.util.List;
import java.util.Optional;

public interface KeywordRepository {

    Keyword save(Keyword keyword);

    Optional<Keyword> findByContent(String content);

    List<Keyword> findKeywordByContentIn(List<String> contents);

    List<Keyword> findTop20Keyword();
}
