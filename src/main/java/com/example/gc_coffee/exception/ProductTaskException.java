package com.example.gc_coffee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductTaskException extends RuntimeException{
    private  String message;
    private  int code;
}
