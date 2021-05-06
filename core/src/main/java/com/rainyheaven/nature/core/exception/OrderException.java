package com.rainyheaven.nature.core.exception;

public class OrderException extends DomainException{
    public OrderException(OrderExceptionType orderExceptionType) {
        super(orderExceptionType.message(), orderExceptionType.name());
    }
}
