package com.example.gc_coffee.entity;

public enum OrderStatus {
    ORDERED("주문이 접수되었습니다."),
    PAYMENT_PENDING("결제가 진행 중입니다."),
    CONFIRMED("당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다."),
    SHIPPED("주문이 발송되었습니다."),
    DELIVERED("주문이 당일 배송됩니다.");
    private final String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
