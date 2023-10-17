package com.adpcoinchange.adpcoinchange.exception;

public class InsufficientAmountOfCoinsException extends RuntimeException {

    public InsufficientAmountOfCoinsException(String message) {
        super(message);
    }
}
