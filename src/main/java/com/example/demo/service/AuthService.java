package com.example.demo.service;

import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthValidate authValidate;
    private final PasswordEncoder passwordEncoder;

    public void register(MemberCreateRequest request) {
        Member member = new Member(
                request.getUserId(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(),
                request.getAccount()
        );

        memberRepository.save(member);
    }

    public void logIn(LogInRequest request) {
        authValidate.validateLogInCorrectPassword(request);
    }
}
