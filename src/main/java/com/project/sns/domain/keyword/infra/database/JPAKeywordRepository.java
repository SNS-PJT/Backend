package com.project.sns.domain.keyword.infra.database;

import com.project.sns.domain.keyword.domain.Keyword;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAKeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByContent(String keyword);

    List<Keyword> findKeywordByContentIn(List<String> contents);

    List<Keyword> findTop20ByOrderByKeywordCountDesc();
}
