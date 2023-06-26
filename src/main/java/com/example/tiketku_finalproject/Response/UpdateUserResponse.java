package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponse {
    private UUID uuid_user;
    private String email;
    private String full_name;
    private char gender;
    private String phone;
}
