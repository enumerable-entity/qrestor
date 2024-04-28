package com.qrestor.menu.api;


public class RestApiException extends RuntimeException{
    public RestApiException(String message) {
        super(message);
    }
}
