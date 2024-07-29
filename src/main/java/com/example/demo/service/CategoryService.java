package com.example.demo.service;

import com.example.demo.controller.dto.response.CategoryResponse;
import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).get();
        CategoryResponse response = new CategoryResponse(category);

        return response;
    }
}
