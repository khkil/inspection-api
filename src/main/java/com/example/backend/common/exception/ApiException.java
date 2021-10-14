package com.example.backend.common.exception;

public class ApiException extends RuntimeException {

    public ApiException(String msg){
        super(msg);
    }
}
