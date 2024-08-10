package com.example.demo.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private String password;
    private String phoneNumber;
    private String account;

    public boolean checkIsEmpty() {
        if (password == null && phoneNumber == null && account == null)
            return true;

        return false;
    }
}
