package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String full_name;
    private char gender;
    private String phone;
    private String roles;
}
