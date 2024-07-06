package com.testmanagement_api.exception;

public class DuplicateCategoryEntry extends RuntimeException{
    public DuplicateCategoryEntry(String message)
    {
        super(message);
    }
}
