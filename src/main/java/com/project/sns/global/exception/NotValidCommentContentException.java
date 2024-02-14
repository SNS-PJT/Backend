package com.project.sns.global.exception;

public class NotValidCommentContentException extends BusinessException {

    public NotValidCommentContentException(String exceptionMessage) {
        super(400, exceptionMessage);
    }
}
