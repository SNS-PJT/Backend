package com.project.sns.global.exception;

public class S3UploadFailException extends BusinessException {

    public S3UploadFailException(String exceptionMessage) {
        super(500, exceptionMessage);
    }
}
