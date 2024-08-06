package com.example.demo.controller;

import com.example.demo.annotation.SwaggerApiOk;
import com.example.demo.controller.dto.response.CategoryResponse;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category/{id}")
    @SwaggerApiOk(summary = "카테고리 조회", description = "특정 id의 카테고리를 조회합니다.", implementation = CategoryResponse.class)
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        CategoryResponse response = categoryService.getById(id);

        return ResponseEntity.ok(response);
    }
}
