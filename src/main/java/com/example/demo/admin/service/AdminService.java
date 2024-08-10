package com.example.demo.admin.service;

import com.example.demo.domain.member.dto.DetailMemberResponse;
import com.example.demo.domain.review.dto.DetailReviewResponse;
import com.example.demo.domain.like.model.Like;
import com.example.demo.domain.member.model.Member;
import com.example.demo.domain.product.model.Product;
import com.example.demo.domain.review.model.Review;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.domain.like.repository.LikeRepository;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.product.repository.ProductRepository;
import com.example.demo.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public List<DetailMemberResponse> getAllMembers() {
        List<Member> members = memberRepository.findAll();

        if (members.isEmpty())
            throw new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER);

        return members.stream().map(DetailMemberResponse::from).toList();
    }

    @Transactional
    public void deleteMember(String userId) {
        List<Like> likes = likeRepository.findAllByMemberUserId(userId);
        likes.stream().map(Like::getProduct).forEach(Product::decreaseLike);

        memberRepository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DetailReviewResponse> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();

        if (reviews.isEmpty())
            throw new ExceptionGenerator(StatusEnum.NOT_PRESENT_REVIEW);

        return reviews.stream().map(DetailReviewResponse::from).toList();
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public void suspendMember(String userId, LocalDateTime endTime) {
        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        member.suspend(endTime);
    }

    @Transactional
    public void unsuspendMember(String userId) {
        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        member.unsuspend();
    }
}
