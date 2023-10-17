package com.adpcoinchange.adpcoinchange.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoinChangeExceptionHandler {

    @ExceptionHandler(InvalidBillValueException.class)
    public ResponseEntity<String> handleInvalidBillException(InvalidBillValueException invalidBillValueException) {
        return ResponseEntity.badRequest().body(invalidBillValueException.getMessage());
    }

    @ExceptionHandler(InsufficientAmountOfCoinsException.class)
    public ResponseEntity<String> handleInsufficientAmountOfCoinsException(InsufficientAmountOfCoinsException insufficientAmountOfCoinsException) {
        return ResponseEntity.internalServerError().body(insufficientAmountOfCoinsException.getMessage());
    }

    @ExceptionHandler(DatabaseTransactionException.class)
    public ResponseEntity<String> handleDatabaseTransactionException(DatabaseTransactionException databaseTransactionException) {
        return ResponseEntity.internalServerError().body(databaseTransactionException.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleConcurrencyException(RuntimeException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }


}
