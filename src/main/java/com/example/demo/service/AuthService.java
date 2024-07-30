package com.example.demo.service;

import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(MemberCreateRequest request) {
        memberService.join(request);
    }

    @Transactional
    public void logIn(LogInRequest request) {
        if (!passwordEncoder.matches(
                request.getPassword(),
                memberService
                        .getByUserId(request.getUserId())
                        .getPassword())
        )
            throw new ExceptionGenerator(StatusEnum.LOGIN_UNSUCCESSFUL);
    }
}
