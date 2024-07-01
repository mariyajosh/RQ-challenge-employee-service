package com.example.rqchallenge.exception;

public class ExternalServiceException extends RuntimeException{
    public ExternalServiceException(Throwable throwable, String message){
        super(message);
    }
}
