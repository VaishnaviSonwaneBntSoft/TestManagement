package com.testmanagement_api.reponsehandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuccessResponse {
    public SuccessResponse(String string, int i) {
        //TODO Auto-generated constructor stub
    }
    private String Message;
    private int StatusCode;
    private Object ModuleData;
}
