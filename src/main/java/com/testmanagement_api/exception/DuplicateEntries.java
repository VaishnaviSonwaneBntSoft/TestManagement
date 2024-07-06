package com.testmanagement_api.exception;

import java.util.List;

public class DuplicateEntries extends RuntimeException{
    public List<String> queList;
    public DuplicateEntries(List<String> questions){
        queList=questions;
    }
}
