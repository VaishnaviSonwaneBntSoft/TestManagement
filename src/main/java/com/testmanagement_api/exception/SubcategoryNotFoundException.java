package com.testmanagement_api.exception;

public class SubcategoryNotFoundException extends RuntimeException{
    public SubcategoryNotFoundException(String message)
    {
        super(message);
    }
}
