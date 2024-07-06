package com.testmanagement_api.exception;

public class DuplicateSubCategoryEntry extends RuntimeException{
    public DuplicateSubCategoryEntry(String message)
    {
        super(message);
    }
}
