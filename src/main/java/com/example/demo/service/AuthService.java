package com.example.demo.service;

import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.response.DetailMemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DetailMemberResponse register(MemberCreateRequest request) {
        if (request.checkContainNull())
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);

        if (memberRepository.existsByUserId(request.getUserId()))
            throw new ExceptionGenerator(StatusEnum.DUPLICATE_UNIT);

        Member member = new Member(request.getUserId(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(),
                request.getAccount()
        );

        memberRepository.save(member);

        return DetailMemberResponse.from(member);
    }

    @Transactional
    public void logIn(LogInRequest request) {
        Member member = memberRepository.findByUserId(request.getUserId())
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        if (!passwordEncoder.matches(
                request.getPassword(),
                member.getPassword()
        ))
            throw new ExceptionGenerator(StatusEnum.LOGIN_UNSUCCESSFUL);

        if (member.isSuspend())
            throw new ExceptionGenerator(StatusEnum.SUSPENSION_ERROR, member.getSuspensionEndDate());
    }
}
