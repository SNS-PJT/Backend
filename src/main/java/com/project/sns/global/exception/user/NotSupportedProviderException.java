package com.project.sns.global.exception.user;

import com.project.sns.global.exception.BusinessException;

public class NotSupportedProviderException extends BusinessException {

    public NotSupportedProviderException(String exceptionMessage) {
        super(401, exceptionMessage);
    }
}
