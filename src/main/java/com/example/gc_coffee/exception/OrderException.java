package com.example.gc_coffee.exception;

public enum OrderException {
    NOT_FOUND("Order NOT FOUND", 404),
    CREATION_FAILED("Order CREATION FAILED", 400),
    REMOVAL_FAILED("Order REMOVAL FAILED", 400),
    UPDATE_FAILED("Order UPDATE FAILED", 500),
    ITEM_NOT_FOUND("Order ITEM NOT FOUND", 404);


    private final OrderTaskException orderTaskException;

    OrderException(String message, int code) {
        this.orderTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}