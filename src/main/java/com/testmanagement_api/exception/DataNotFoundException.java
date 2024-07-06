package com.testmanagement_api.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message)
    {
        super(message);
    }
}
