package com.example.demo.controller.Dto.Request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private String userId;
    private String password;
    private String phoneNumber;
    private String account;
}
