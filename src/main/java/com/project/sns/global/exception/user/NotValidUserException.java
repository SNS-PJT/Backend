package com.project.sns.global.exception.user;

import com.project.sns.global.exception.BusinessException;

public class NotValidUserException extends BusinessException {

    public NotValidUserException(String exceptionMessage) {
        super(403, exceptionMessage);
    }
}
