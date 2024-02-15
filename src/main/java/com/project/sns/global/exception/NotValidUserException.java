package com.project.sns.global.exception;

public class NotValidUserException extends BusinessException {

    public NotValidUserException(String exceptionMessage) {
        super(403, exceptionMessage);
    }
}
