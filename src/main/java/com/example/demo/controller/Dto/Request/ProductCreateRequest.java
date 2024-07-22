package com.example.demo.controller.Dto.Request;

import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private Long sellerId;
    private Long categoryId;
    private String title;
    private String imageUrl;
    private String content;
    private Integer price;
}
