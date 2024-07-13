package com.acker.busticketbackend.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(Integer id) {
        this("User with the ID: " + id.toString() + " is not Found");
    }
}