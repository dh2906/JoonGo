package com.example.demo.service;

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

import java.time.LocalDateTime;
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
            throw new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER);

        return members.stream().map(MemberResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse getByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        return MemberResponse.from(member);
    }

    @Transactional
    public DetailMemberResponse update(String userId, MemberUpdateRequest request) {
        if (request.checkIsEmpty())
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);

        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

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

    @Transactional
    public void checkSuspendedMembers() {
        List<Member> members = memberRepository.findAll();

        for (Member m : members) {
            if (m.isSuspend() && LocalDateTime.now().isAfter(m.getSuspensionEndDate()))
                m.unsuspend();
        }
    }
}
