package com.example.demo.domain.category.dto;

import com.example.demo.domain.category.model.Category;
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
