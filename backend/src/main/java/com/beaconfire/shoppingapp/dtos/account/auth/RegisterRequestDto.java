package com.beaconfire.shoppingapp.dtos.account.auth;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class RegisterRequestDto {
    @NotBlank(message = "Please, enter your username!")
    @Length(min = 3, message = "Username must be minimum 3 in length!")
    private String username;

    @NotBlank(message = "Please, enter your email!")
    @Email(message = "Your email is invalid!")
    private String email;

    @NotBlank(message = "Please, enter your password!")
    @Length(min = 3, message = "Password must be minimum 3 in length!")
    private String password;
}
