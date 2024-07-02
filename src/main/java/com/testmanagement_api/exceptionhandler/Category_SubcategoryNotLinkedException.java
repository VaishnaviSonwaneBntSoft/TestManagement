package com.testmanagement_api.exceptionhandler;

public class Category_SubcategoryNotLinkedException extends RuntimeException{
    public Category_SubcategoryNotLinkedException(String message)
    {
        super(message);
    }
}
