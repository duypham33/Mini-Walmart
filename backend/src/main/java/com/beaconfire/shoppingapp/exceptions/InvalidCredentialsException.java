package com.beaconfire.shoppingapp.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InvalidCredentialsException extends RuntimeException{
    private final Long userId;
    private final String username;
    private final String incorrectPassword;

    public InvalidCredentialsException(
            Long userId, String username, String incorrectPassword
    ){
        super("Incorrect credentials, please try again!");
        this.userId = userId;
        this.username = username;
        this.incorrectPassword = incorrectPassword;
    }
}
