package com.example.demo.service;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();

        return members.stream().map(MemberResponse::from).toList();
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER));

        return MemberResponse.from(member);
    }

    public Member getByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER));

        return member;
    }

    @Transactional
    public MemberResponse join(MemberCreateRequest request) {
        Member member = new Member(request);
        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER));
        member.update(request);

        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
