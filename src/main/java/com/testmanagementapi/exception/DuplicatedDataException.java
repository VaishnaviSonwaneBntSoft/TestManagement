package com.testmanagementapi.exception;

public class DuplicatedDataException extends RuntimeException{
    public DuplicatedDataException(String message)
    {
        super(message);
    }
}