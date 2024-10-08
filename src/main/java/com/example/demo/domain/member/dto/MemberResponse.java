package com.example.demo.domain.member.dto;

import com.example.demo.domain.member.model.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String userId;
    private String role;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.role = member.getRole();
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member);
    }
}