package com.testmanagement_api.exception;

public class CategoryNotFoundException extends RuntimeException{
    
    public CategoryNotFoundException(String message)
    {
        super(message);
    }
}
