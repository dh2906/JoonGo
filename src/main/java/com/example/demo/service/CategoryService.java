package com.example.demo.service;

import com.example.demo.controller.dto.response.CategoryResponse;
import com.example.demo.domain.Category;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
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
