package com.testmanagement_api.exceptionhandler;

public class CategoryNotFoundException extends RuntimeException{
    
    public CategoryNotFoundException(String message)
    {
        super(message);
    }
}
