package com.example.gc_coffee.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final String statusMessage;
    private final T data;

    public ApiResponse(
            final String statusMessage,
            final T data) {
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(final T data) {
        return new ApiResponse<>(
                "성공",
                data
        );
    }

    public static <T> ApiResponse<T> error(final T data) {
        return new ApiResponse<>(
                "실패",
                data
        );
    }

}

