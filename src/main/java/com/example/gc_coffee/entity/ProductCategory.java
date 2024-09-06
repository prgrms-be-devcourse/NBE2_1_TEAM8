package com.example.gc_coffee.entity;

import lombok.Getter;

@Getter
public enum ProductCategory {
    COLUMBIA_COFFEE("Columbia Coffee"),
    COLUMBIA_QUINDIO("Columbia Quindio"),
    BRAZIL_SERRA_DO_COFFEE("Brazil Serra Do Coffee"),
    ETHIOPIA_SIDAMO("Ethiopia Sidamo");

    private final String status;

    ProductCategory(final String status) {
        this.status = status;
    }
}
