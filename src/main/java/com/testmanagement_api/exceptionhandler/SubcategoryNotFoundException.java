package com.testmanagement_api.exceptionhandler;

public class SubcategoryNotFoundException extends RuntimeException{
    public SubcategoryNotFoundException(String message)
    {
        super(message);
    }
}
