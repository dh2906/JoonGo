package com.example.demo.controller.dto.request;

import lombok.Getter;

@Getter
public class MemberCreateRequest {
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
