package com.beaconfire.shoppingapp.dtos.account.auth;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class LoginRequestDto {
    @NotBlank(message = "Please, enter your username!")
    private String username;

    @NotBlank(message = "Please, enter your password!")
    private String password;
}
