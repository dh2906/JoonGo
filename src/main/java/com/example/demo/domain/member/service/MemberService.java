package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.MemberUpdateRequest;
import com.example.demo.domain.member.dto.DetailMemberResponse;
import com.example.demo.domain.member.dto.MemberResponse;
import com.example.demo.domain.like.model.Like;
import com.example.demo.domain.member.model.Member;
import com.example.demo.domain.product.model.Product;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.domain.like.repository.LikeRepository;
import com.example.demo.domain.member.repository.MemberRepository;
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
    public MemberResponse update(String userId, MemberUpdateRequest request) {
        if (request.getPassword() == null)
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);

        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        member.updatePassword(passwordEncoder.encode(request.getPassword()));

        memberRepository.save(member);

        return MemberResponse.from(member);
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
