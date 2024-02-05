package com.project.sns.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String keyword;

    @Column(name = "keyword_count", columnDefinition = "int default 1")
    private Integer keywordCount;

    @Builder
    private Keyword(String keyword) {
        this.keyword = keyword;
    }

    public static Keyword createKeyword(String keyword) {
        return Keyword.builder()
                      .keyword(keyword)
                      .build();
    }

    public void addKeywordCount() {
        this.keywordCount++;
    }

    public void subtractKeywordCount() {
        if (this.keywordCount > 0) {
            this.keywordCount--;
        }
    }

    @PrePersist
    private void setDefaultValues() {
        this.keywordCount = this.keywordCount == null ? 1 : this.keywordCount;
    }


}
