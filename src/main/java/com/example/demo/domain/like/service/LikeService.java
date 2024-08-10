package com.example.demo.domain.like.service;

import com.example.demo.domain.like.dto.LikeResponse;
import com.example.demo.domain.like.model.Like;
import com.example.demo.domain.member.model.Member;
import com.example.demo.domain.product.model.Product;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.domain.like.repository.LikeRepository;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.product.repository.ProductRepository;
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

        if (likes == null || likes.isEmpty()) {
            throw new ExceptionGenerator(StatusEnum.NOT_PRESENT_LIKE);
        }
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
