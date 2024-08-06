package com.example.demo.service;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.DetailMemberResponse;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Like;
import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();

        if (members.isEmpty())
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER);

        return members.stream().map(MemberResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER));

        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse getByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER));

        return MemberResponse.from(member);
    }

    @Transactional
    public DetailMemberResponse join(MemberCreateRequest request) {
        if (request.isEmpty())
            throw new ExceptionGenerator(StatusEnum.CREATE_OR_EDIT_EMPTY_REQUEST);

        if (memberRepository.existsByUserId(request.getUserId()))
            throw new ExceptionGenerator(StatusEnum.CREATE_OR_EDIT_DUPLICATE_UNIT);

        Member member = new Member(request.getUserId(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(),
                request.getAccount()
        );

        memberRepository.save(member);

        return DetailMemberResponse.from(member);
    }

    @Transactional
    public DetailMemberResponse update(String userId, MemberUpdateRequest request) {
        if (request.isEmpty())
            throw new ExceptionGenerator(StatusEnum.CREATE_OR_EDIT_EMPTY_REQUEST);

        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER));

        if (request.isEmpty())
            throw new ExceptionGenerator(StatusEnum.CREATE_OR_EDIT_EMPTY_REQUEST);

        if (request.getPassword() != null)
            member.updatePassword(passwordEncoder.encode(request.getPassword()));

        if (request.getPhoneNumber() != null)
            member.updatePhoneNumber(request.getPhoneNumber());

        if (request.getAccount() != null)
            member.udpateAccount(request.getAccount());

        memberRepository.save(member);

        return DetailMemberResponse.from(member);
    }

    @Transactional
    public void delete(String userId) {
        List<Like> likes = likeRepository.findAllByMemberUserId(userId);
        likes.stream().map(Like::getProduct).forEach(Product::decreaseLike);

        memberRepository.deleteByUserId(userId);
    }

    @Transactional(readOnly = true)
    public String getPasswordByUserId(String userId) {
        return memberRepository.findByUserId(userId).get().getPassword();
    }
}
