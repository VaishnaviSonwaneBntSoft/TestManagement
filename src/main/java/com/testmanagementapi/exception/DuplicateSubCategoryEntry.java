package com.testmanagementapi.exception;

public class DuplicateSubCategoryEntry extends RuntimeException{
    public DuplicateSubCategoryEntry(String message)
    {
        super(message);
    }
}
