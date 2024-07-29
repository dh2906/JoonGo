package com.example.demo.controller.dto.request;


import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private String userId;
    private String password;
    private String phoneNumber;
    private String account;

    public boolean isEmpty() {

    }
}
