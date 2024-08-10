package com.example.demo.global.auth;

import lombok.Getter;

@Getter
public class LogInRequest {
    private String userId;
    private String password;

    public boolean isEmpty() {
        if (userId.isEmpty() && password.isEmpty())
            return true;

        return false;
    }
}
