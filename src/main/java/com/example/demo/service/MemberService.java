package com.example.demo.service;

import com.example.demo.controller.Dto.Request.MemberCreateRequest;
import com.example.demo.controller.Dto.Request.MemberUpdateRequest;
import com.example.demo.controller.Dto.Response.MemberResponse;
import com.example.demo.domain.Member;
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
        Member member = memberRepository.findById(id).get();

        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse join(MemberCreateRequest request) {
        Member member = new Member(request);
        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).get();
        member.update(request);

        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
