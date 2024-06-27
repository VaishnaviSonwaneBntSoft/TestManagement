package com.testmanagement_api.exceptionhandler;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message)
    {
        super(message);
    }
}
