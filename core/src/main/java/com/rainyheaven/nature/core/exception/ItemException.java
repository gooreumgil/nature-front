package com.rainyheaven.nature.core.exception;


public class ItemException extends DomainException{
    public ItemException(ItemExceptionType itemExceptionType) {
        super(itemExceptionType.message(), itemExceptionType.name());
    }
}
