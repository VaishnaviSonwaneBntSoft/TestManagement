package com.testmanagementapi.exception;

public class CategorySubcategoryNotLinkedException extends RuntimeException{
    public CategorySubcategoryNotLinkedException(String message)
    {
        super(message);
    }
}
