package com.example.demo.service;

import com.example.demo.controller.Dto.Response.CategoryResponse;
import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).get();
        CategoryResponse response = new CategoryResponse(category);

        return response;
    }
}
