package com.example.demo.service;

import com.example.demo.controller.dto.response.DetailMemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public List<DetailMemberResponse> getAllMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream().map(DetailMemberResponse::from).toList();
    }

    public void deleteMember(String userId) {
        memberRepository.deleteByUserId(userId);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
