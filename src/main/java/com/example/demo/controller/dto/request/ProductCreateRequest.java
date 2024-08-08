package com.example.demo.controller.dto.request;

import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private Long categoryId;
    private String title;
    private String imageUrl;
    private String content;
    private Integer price;

    public boolean checkContainNull() {
        if (categoryId == null || title == null || content == null || price == null)
            return true;

        return false;
    }
}
