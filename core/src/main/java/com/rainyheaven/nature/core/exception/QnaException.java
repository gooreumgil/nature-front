package com.rainyheaven.nature.core.exception;

public class QnaException extends DomainException {
    public QnaException(QnaExceptionType qnaExceptionType) {
        super(qnaExceptionType.message(), qnaExceptionType.name());
    }
}
