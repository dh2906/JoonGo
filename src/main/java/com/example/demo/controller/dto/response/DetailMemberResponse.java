package com.example.demo.controller.dto.response;

import com.example.demo.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DetailMemberResponse {
    private Long id;
    private String userId;
    private String phoneNumber;
    private String account;
    private String role;
    private boolean isSuspend;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime suspensionEndDate;

    public DetailMemberResponse(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.phoneNumber = member.getPhoneNumber();
        this.account = member.getAccount();
        this.role = member.getRole();
        this.isSuspend = member.isSuspend();
        this.suspensionEndDate = member.getSuspensionEndDate();
    }

    public static DetailMemberResponse from(Member member) {
        return new DetailMemberResponse(member);
    }
}
