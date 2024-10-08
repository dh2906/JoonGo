package com.example.demo.domain.review.service;

import com.example.demo.domain.review.dto.ReviewCreateRequest;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.member.model.Member;
import com.example.demo.domain.review.dto.ReviewUpdateRequest;
import com.example.demo.domain.review.model.Review;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponse> getAllByUserId(String sellerUserId) {
        memberRepository.findByUserId(sellerUserId)
                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        List<Review> reviews = reviewRepository.findAllBySellerUserId(sellerUserId);

        return reviews.stream().map(ReviewResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Review getById(Long id) {
        Review review = reviewRepository.findById(id)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_REVIEW));

        return review;
    }

    @Transactional
    public ReviewResponse create(String authorUserId, String sellerUserId, ReviewCreateRequest request) {
        if (request.isEmpty())
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);

        Member author = memberRepository.findByUserId(authorUserId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_AUTHOR));

        Member seller = memberRepository.findByUserId(sellerUserId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_SELLER));

        if ( (request.getScore() < 0) || (request.getScore() > 10) )
            throw new ExceptionGenerator(StatusEnum.SCORE_OUT_OF_RANGE);

        Review review = new Review(author, seller, request);

        reviewRepository.save(review);

        return ReviewResponse.from(review);
    }

    @Transactional
    public ReviewResponse update(Long id, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(id)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_REVIEW));

        if (request.isEmpty())
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);

        if (request.getContent() != null)
            review.updateContent(request.getContent());

        if (request.getScore() != null) {
            if ((request.getScore() < 0) || (request.getScore() > 10))
                throw new ExceptionGenerator(StatusEnum.SCORE_OUT_OF_RANGE);

            review.updateScore(request.getScore());
        }

        reviewRepository.save(review);

        return ReviewResponse.from(review);
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
