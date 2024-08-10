package com.example.demo.domain.category.service;

import com.example.demo.domain.category.dto.CategoryResponse;
import com.example.demo.domain.category.model.Category;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.domain.category.repository.CategoryRepository;
import com.example.demo.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_CATEGORY));
        CategoryResponse response = new CategoryResponse(category);

        return response;
    }
}
