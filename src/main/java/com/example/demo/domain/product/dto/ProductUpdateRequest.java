package com.example.demo.domain.product.dto;

import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    private Long categoryId;
    private String title;
    private String imageUrl;
    private String content;
    private Integer price;

    public boolean checkIsEmpty() {
        if (categoryId == null && title == null && content == null && price == null && imageUrl == null)
            return true;

        return false;
    }
}
