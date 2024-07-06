package com.testmanagementapi.exception;

public class SubcategoryNotFoundException extends RuntimeException{
    public SubcategoryNotFoundException(String message)
    {
        super(message);
    }
}
