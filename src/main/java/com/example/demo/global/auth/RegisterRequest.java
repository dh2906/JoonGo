package com.example.demo.global.auth;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String userId;
    private String password;
    private String phoneNumber;
    private String account;

    public boolean checkContainNull() {
        if (userId == null || password == null || phoneNumber == null || account == null)
            return true;

        return false;
    }
}
