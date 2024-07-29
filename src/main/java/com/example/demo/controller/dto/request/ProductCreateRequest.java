package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    @NotNull
    private Long sellerId;

    @NotNull
    private Long categoryId;

    @NotNull
    private String title;

    private String imageUrl;

    private String content;

    @NotNull
    private Integer price;
}
