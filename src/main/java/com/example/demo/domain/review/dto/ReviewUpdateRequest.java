package com.example.demo.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewUpdateRequest {
    private String content;
    private Integer score;

    public boolean isEmpty() {
        if (content == null && score == null)
            return true;

        return false;
    }
}
