package com.testmanagement_api.exception;

public class DuplicatedDataException extends RuntimeException{
    public DuplicatedDataException(String message)
    {
        super(message);
    }
}
