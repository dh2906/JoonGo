package com.example.demo.service;

import com.example.demo.controller.dto.response.LikeResponse;
import com.example.demo.domain.Like;
import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<LikeResponse> getByUserId(String userId) {
        List<Like> likes = likeRepository.findAllByMemberUserId(userId);
        return likes.stream().map(LikeResponse::from).toList();
    }

    @Transactional
    public void toggleLike(String userId, Long productId) {
        Member member = memberRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_PRODUCT));

        if (likeRepository.existsByMemberUserIdAndProductId(userId, productId)) { // 이미 좋아요를 해둔 상황
            product.decreaseLike();
            likeRepository.deleteByMemberUserIdAndProductId(userId, productId);
        }

        else { // 좋아요를 안한 상황
            product.increaseLike();
            Like like = new Like(member, product);
            likeRepository.save(like);
        }
    }
}
