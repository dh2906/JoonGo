package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewCreateRequest {
    @NotNull
    private String content;

    @NotNull
    private int score;
}
