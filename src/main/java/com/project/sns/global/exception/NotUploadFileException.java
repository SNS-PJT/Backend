package com.project.sns.global.exception;

public class NotUploadFileException extends BusinessException {

    public NotUploadFileException(String exceptionMessage) {
        super(403, exceptionMessage);
    }
}
