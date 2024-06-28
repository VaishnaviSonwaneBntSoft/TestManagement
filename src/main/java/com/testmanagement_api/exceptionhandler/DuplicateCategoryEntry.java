package com.testmanagement_api.exceptionhandler;

public class DuplicateCategoryEntry extends RuntimeException{
    public DuplicateCategoryEntry(String message)
    {
        super(message);
    }
}
