package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewCreateRequest {
    private String content;
    private int score;

    public boolean isEmpty() {
        if (content == null || content.isEmpty())
            return true;

        return false;
    }
}
