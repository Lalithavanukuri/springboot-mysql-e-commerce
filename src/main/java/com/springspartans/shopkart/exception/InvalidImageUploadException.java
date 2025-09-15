package com.springspartans.shopkart.exception;

public class InvalidImageUploadException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public InvalidImageUploadException(String message) {
        super(message);
    }
}