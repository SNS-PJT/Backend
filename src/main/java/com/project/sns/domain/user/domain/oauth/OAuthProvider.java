package com.project.sns.domain.user.domain.oauth;

import com.project.sns.global.exception.user.NotSupportedProviderException;

public enum OAuthProvider {
    KAKAO;

    public static OAuthProvider of(String provider) {
        try {
            return OAuthProvider.valueOf(provider);
        } catch (IllegalArgumentException e) {
            throw new NotSupportedProviderException("지원하지 않는 oauth 제공자입니다.");
        }
    }

}
