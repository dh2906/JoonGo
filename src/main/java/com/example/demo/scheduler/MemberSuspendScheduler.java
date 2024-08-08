package com.example.demo.scheduler;

import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberSuspendScheduler {

    private final MemberService memberService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkMemberStatus() {
        memberService.checkSuspendedMembers();
    }
}