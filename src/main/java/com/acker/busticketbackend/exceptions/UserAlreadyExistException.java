package com.acker.busticketbackend.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message){
        super(message);
    }
}
