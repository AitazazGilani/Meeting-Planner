package com.example.app.database;

public class RowDoesNotExistException extends Exception {
    public RowDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}