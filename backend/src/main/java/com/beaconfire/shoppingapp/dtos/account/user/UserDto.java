package com.beaconfire.shoppingapp.dtos.account.user;

import com.beaconfire.shoppingapp.entities.account.user.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String token;

    public static UserDto fromUser(User user){
        if(user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getRole())
                .build();
    }

    public Boolean isAdmin(){
        return this.getRole().equals("ADMIN");
    }
}