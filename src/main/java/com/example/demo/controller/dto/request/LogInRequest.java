package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
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
