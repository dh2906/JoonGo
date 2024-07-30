package com.example.demo.controller.dto.response;

import com.example.demo.domain.Like;
import lombok.Getter;

@Getter
public class LikeResponse {
    private Long productId;

    public LikeResponse(Like like) {
        this.productId = like.getProduct().getId();
    }

    public static LikeResponse from(Like like) {
        return new LikeResponse(like);
    }
}
