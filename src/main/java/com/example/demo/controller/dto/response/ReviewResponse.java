package com.example.demo.controller.dto.response;

import com.example.demo.domain.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {
    private Long id;
    private String authorId;
    private String content;
    private int score;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.authorId = review.getAuthor().getUserId();
        this.content = review.getContent();
        this.score = review.getScore();
    }

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(review);
    }
}
