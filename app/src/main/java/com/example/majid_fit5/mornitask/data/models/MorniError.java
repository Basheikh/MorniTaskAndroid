package com.example.majid_fit5.mornitask.data.models;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public class
MorniError {
    private int code;
    private String message="";

    public MorniError(){

    }

    public MorniError(int code, String message){
        this.code=code;
        this.message=message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
