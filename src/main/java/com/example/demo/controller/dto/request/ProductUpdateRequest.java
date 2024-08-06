package com.example.demo.controller.dto.request;

import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    private Long categoryId;
    private String title;
    private String imageUrl;
    private String content;
    private Integer price;

    public boolean isEmpty() {
        if (categoryId == null && title.isEmpty() && content.isEmpty() && price == null && imageUrl.isEmpty())
            return true;

        return false;
    }
}
