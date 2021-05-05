package com.rainyheaven.nature.core.exception;

public class EmailVerifyException extends DomainException{
    public EmailVerifyException(EmailVerifyExceptionType emailVerifyExceptionType) {
        super(emailVerifyExceptionType.message(), emailVerifyExceptionType.name());
    }
}
