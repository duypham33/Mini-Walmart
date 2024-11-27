package com.beaconfire.shoppingapp.entities.account.user;

import lombok.*;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class UserRole {
    @Id
    private String role;
}
