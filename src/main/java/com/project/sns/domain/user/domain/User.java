package com.project.sns.domain.user.domain;

import com.project.sns.domain.keyword.domain.Keyword;
import com.project.sns.domain.user.domain.keyword.UserKeyword;
import com.project.sns.domain.user.domain.oauth.OAuthProfile;
import com.project.sns.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Getter
    private Long id;
    
    @Embedded
    private OAuthProfile oAuthProfile;

    @Column(nullable = false, length = 30, unique = true)
    @Getter
    private String nickname;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Gender gender;

    @Column(length = 200)
    @Getter
    private String profilePath;

    @Column(nullable = false, length = 100)
    private String profileMessage;

    @Column(nullable = false, name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @Column(name = "last_upload_date")
    private LocalDateTime lastUploadDate;

    @Column(name = "refresh_token", length = 200)
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserKeyword> userKeywords = new ArrayList<>();

    @Builder
    private User(Long id, OAuthProfile oAuthProfile, String nickname, String email, LocalDate birth,
            Gender gender, String profilePath, String profileMessage, Boolean isDeleted,
            LocalDateTime lastUploadDate, String refreshToken, List<UserKeyword> userKeywords) {
        this.id = id;
        this.oAuthProfile = oAuthProfile;
        this.nickname = nickname;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.profilePath = profilePath;
        this.profileMessage = profileMessage;
        this.isDeleted = isDeleted;
        this.lastUploadDate = lastUploadDate;
        this.refreshToken = refreshToken;
        this.userKeywords = userKeywords;
    }


    public static User createUser(OAuthProfile oAuthProfile, String nickname, String email,
            LocalDate birth,
            Gender gender,
            String profilePath, String profileMessage) {
        return User.builder()
                   .oAuthProfile(oAuthProfile)
                   .nickname(nickname)
                   .email(email)
                   .birth(birth)
                   .gender(gender)
                   .profilePath(profilePath)
                   .profileMessage(profileMessage)
                   .build();
    }

    public void addKeywords(List<Keyword> keywords) {
        List<UserKeyword> userKeywords = UserKeyword.createUserKeywords(this, keywords);
        this.userKeywords.addAll(userKeywords);
    }

    @PrePersist
    private void setDefaultValues() {
        this.isDeleted = this.isDeleted != null && this.isDeleted;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
