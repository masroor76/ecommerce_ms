package com.baloch.products.core.exceptions;

public class CustomException extends RuntimeException{
    private String errorMessage;

    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}
