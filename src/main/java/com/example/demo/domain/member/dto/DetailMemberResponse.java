package com.example.demo.domain.member.dto;

import com.example.demo.domain.member.model.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DetailMemberResponse {
    private Long id;
    private String userId;
    private String role;
    private boolean isSuspend;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime suspensionEndDate;

    public DetailMemberResponse(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.role = member.getRole();
        this.isSuspend = member.isSuspend();
        this.suspensionEndDate = member.getSuspensionEndDate();
    }

    public static DetailMemberResponse from(Member member) {
        return new DetailMemberResponse(member);
    }
}
