package com.testmanagementapi.exception;

public class DuplicateCategoryEntry extends RuntimeException{
    public DuplicateCategoryEntry(String message)
    {
        super(message);
    }
}
