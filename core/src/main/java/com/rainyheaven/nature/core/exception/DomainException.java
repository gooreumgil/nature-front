package com.rainyheaven.nature.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DomainException extends RuntimeException {

    private String type;

    public DomainException(String message, String type) {
        super(message);
        this.type = type;
    }
}
