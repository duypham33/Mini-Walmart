package com.beaconfire.shoppingapp.dtos.account.auth;
import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginResponseDto {
    private UserDto user;
    private Boolean isSuccess;
    private String message;
}