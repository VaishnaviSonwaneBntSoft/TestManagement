package com.testmanagement_api.exceptionhandler;

public class DuplicateSubCategoryEntry extends RuntimeException{
    public DuplicateSubCategoryEntry(String message)
    {
        super(message);
    }
}
