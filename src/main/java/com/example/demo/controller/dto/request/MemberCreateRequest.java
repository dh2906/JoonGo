package com.example.demo.controller.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberCreateRequest {
    private String userId;
    private String password;
    private String phoneNumber;
    private String account;

    public boolean isEmpty() {
        if (userId.isEmpty() && password.isEmpty() && phoneNumber.isEmpty() && account.isEmpty())
            return true;

        return false;
    }
}
