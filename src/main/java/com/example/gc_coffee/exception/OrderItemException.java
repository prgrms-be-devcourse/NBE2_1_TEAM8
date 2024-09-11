package com.example.gc_coffee.exception;

public enum OrderItemException {
    NOT_FOUND("OrderItem NOT FOUND", 404),
    NOT_REMOVED("OrderItem NOT REMOVED", 400),
    NOT_MODIFIED("OrderItem NOT MODIFIED", 500);


    private final OrderItemTaskException orderItemTaskException;

    OrderItemException(String message, int code) {
        this.orderItemTaskException = new OrderItemTaskException(message, code);
    }

    public OrderItemTaskException get() {
        return orderItemTaskException;
    }

}