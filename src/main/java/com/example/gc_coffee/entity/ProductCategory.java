package com.example.gc_coffee.entity;

import lombok.Getter;

@Getter
public enum ProductCategory {
    COFFEE_BEAN_PACKAGE("Coffee Bean Package");

    private final String status;

    ProductCategory(final String status) {
        this.status = status;
    }
}
