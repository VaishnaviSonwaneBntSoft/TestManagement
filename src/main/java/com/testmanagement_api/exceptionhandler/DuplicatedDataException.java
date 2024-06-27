package com.testmanagement_api.exceptionhandler;

public class DuplicatedDataException extends RuntimeException{
    public DuplicatedDataException(String message)
    {
        super(message);
    }
}
