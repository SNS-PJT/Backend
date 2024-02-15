package com.project.sns.global.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(String exceptionMessage) {
        super(404, exceptionMessage);
    }
}
