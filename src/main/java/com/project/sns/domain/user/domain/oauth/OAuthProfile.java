package com.project.sns.domain.user.domain.oauth;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthProfile {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "oauth_provider", length = 10)
    @Getter
    private OAuthProvider oauthProvider;

    @Column(nullable = false, unique = true, name = "oauth_id", length = 100)
    @Getter
    private String oauthId;

    public OAuthProfile(OAuthProvider oauthProvider, String oauthId) {
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
    }
}
