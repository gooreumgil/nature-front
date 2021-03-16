package com.rainyheaven.nature.core.exception;

public class UserException extends DomainException {

    public UserException(UserExceptionType userExceptionType) {
        super(userExceptionType.message(), userExceptionType.name());
    }
}
