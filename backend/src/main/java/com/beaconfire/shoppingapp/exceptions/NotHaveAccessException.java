package com.beaconfire.shoppingapp.exceptions;

public class NotHaveAccessException extends RuntimeException{
    public NotHaveAccessException(String message){
        super(message);
    }
}
