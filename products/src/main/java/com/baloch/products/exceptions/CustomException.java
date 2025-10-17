package com.baloch.products.exceptions;

public class CustomException extends RuntimeException{
    private String errorMessage;

    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}
