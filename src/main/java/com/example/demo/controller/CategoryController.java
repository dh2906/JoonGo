package com.example.demo.controller;

import com.example.demo.controller.dto.response.CategoryResponse;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "해당 카테고리를 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "카테고리 조회")
    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        CategoryResponse response = categoryService.getById(id);

        return ResponseEntity.ok(response);
    }
}
