package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LogInRequest {
    @NotNull
    private String userId;

    @NotNull
    private String password;
}
