package com.example.demo.controller.Dto.Request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberCreateRequest {
    @NotNull
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String account;
}
