package com.example.demo.domain.review.dto;

import com.example.demo.domain.review.model.Review;
import lombok.Getter;

@Getter
public class DetailReviewResponse {
    private Long id;
    private String authorId;
    private String SellerId;
    private String content;
    private int score;

    public DetailReviewResponse(Review review) {
        this.id = review.getId();
        this.authorId = review.getAuthor().getUserId();
        this.SellerId = review.getSeller().getUserId();
        this.content = review.getContent();
        this.score = review.getScore();
    }

    public static DetailReviewResponse from(Review review) {
        return new DetailReviewResponse(review);
    }
}
