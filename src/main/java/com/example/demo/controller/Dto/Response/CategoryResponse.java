package com.example.demo.controller.Dto.Response;

import com.example.demo.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponse {
    private Long id;
    private String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
