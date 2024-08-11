package com.example.demo.global.auth;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String userId;
    private String password;

    public boolean checkContainNull() {
        if (userId == null || password == null)
            return true;

        return false;
    }
}
