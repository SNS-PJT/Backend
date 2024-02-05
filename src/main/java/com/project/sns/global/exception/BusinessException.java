package com.project.sns.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int statusCode;

    public BusinessException(int statusCode, String exceptionMessage) {
        super(exceptionMessage);
        this.statusCode = statusCode;
    }
}
