package com.example.gc_coffee.exception;

public enum ProductException {
    NOT_FOUND("Product NOT FOUND", 404),
    NOT_REMOVED("Product NOT REMOVED", 400),
    NOT_MODIFIED("Product NOT MODIFIED", 500);


    private final ProductTaskException productTaskException;

    ProductException(String message, int code) {
        this.productTaskException = new ProductTaskException(message, code);
    }

    public ProductTaskException get() {
        return productTaskException;
    }
}