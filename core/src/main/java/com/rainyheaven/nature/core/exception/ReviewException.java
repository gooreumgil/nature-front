package com.rainyheaven.nature.core.exception;

public class ReviewException extends DomainException {
    public ReviewException(ReviewExceptionType reviewExceptionType) {
        super(reviewExceptionType.message(), reviewExceptionType.name());
    }
}
