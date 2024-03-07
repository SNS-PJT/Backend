package com.project.sns.global.exception.user;

import com.project.sns.global.exception.BusinessException;

public class NotValidKeywordContentException extends BusinessException {

    public NotValidKeywordContentException(String exceptionMessage) {
        super(400, exceptionMessage);
    }
}
