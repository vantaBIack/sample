package com.adpcoinchange.adpcoinchange.exception;

public class DatabaseTransactionException extends RuntimeException {
    public DatabaseTransactionException(String message) {
        super(message);
    }
}
