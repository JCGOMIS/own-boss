package com.ownboss.own_boss.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email){
        super("User not found with email " + email);
    }

}
