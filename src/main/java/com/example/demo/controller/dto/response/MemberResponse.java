package com.example.demo.controller.dto.response;

import com.example.demo.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String userId;
    private String role;
    private boolean isSuspend;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.role = member.getRole();
        this.isSuspend = member.isSuspend();
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member);
    }
}