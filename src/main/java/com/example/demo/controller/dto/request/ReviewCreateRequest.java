package com.example.demo.controller.dto.request;

import lombok.Getter;

@Getter
public class ReviewCreateRequest {
    private String content;
    private int score;

    public boolean isEmpty() {
        if (content == null)
            return true;

        return false;
    }
}
