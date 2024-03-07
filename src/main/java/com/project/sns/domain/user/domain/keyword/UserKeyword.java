package com.project.sns.domain.user.domain.keyword;

import com.project.sns.domain.keyword.domain.Keyword;
import com.project.sns.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "user_keyword")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @Builder
    private UserKeyword(User user, Keyword keyword) {
        this.user = user;
        this.keyword = keyword;
    }

    public static List<UserKeyword> createUserKeywords(User user, List<Keyword> keywords) {
        return keywords.stream()
                       .map(keyword -> {
                           return UserKeyword.builder()
                                             .user(user)
                                             .keyword(keyword)
                                             .build();
                       })
                       .collect(Collectors.toList());
    }
}
