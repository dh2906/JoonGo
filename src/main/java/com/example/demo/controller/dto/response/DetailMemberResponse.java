package com.example.demo.controller.dto.response;

import com.example.demo.domain.Member;
import lombok.Getter;

@Getter
public class DetailMemberResponse {
    private Long id;
    private String userId;
    private String phoneNumber;
    private String account;

    public DetailMemberResponse(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.phoneNumber = member.getPhoneNumber();
        this.account = member.getAccount();
    }

    public static DetailMemberResponse from(Member member) {
        return new DetailMemberResponse(member);
    }
}
