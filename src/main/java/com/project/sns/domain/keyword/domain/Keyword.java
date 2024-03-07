package com.project.sns.domain.keyword.domain;

import com.project.sns.global.exception.user.NotValidKeywordContentException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    private static final int MAX_KEYWORD_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(unique = true, nullable = false, length = MAX_KEYWORD_LENGTH)
    @Getter
    private String content;

    @Column(name = "keyword_count", columnDefinition = "int default 1")
    private Integer keywordCount;


    public Keyword(String content) {
        if (isNotValidKeyword(content)) {
            throw new NotValidKeywordContentException("키워드 형식을 확인해주세요.");
        }
        this.content = content;
    }

    private boolean isNotValidKeyword(String content) {
        return Objects.isNull(content) || content.isBlank()
                || content.length() > MAX_KEYWORD_LENGTH;
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
