package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private Long categoryId;
    private String title;
    private String imageUrl;
    private String content;
    private Integer price;

    public boolean isEmpty() {
        if (categoryId == null && title.isEmpty() && price == null)
            return true;

        return false;
    }
}
