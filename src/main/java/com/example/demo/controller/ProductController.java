package com.example.demo.controller;

import com.example.demo.controller.dto.request.ProductCreateRequest;
import com.example.demo.controller.dto.request.ProductUpdateRequest;
import com.example.demo.controller.dto.response.ProductResponse;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse response = productService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products") // 카테고리를 파라미터에 넣은 경우
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @RequestParam(name = "parent", required = false) Long parentId,
            @RequestParam(name = "child", required = false) Long childId) {
        List<ProductResponse> response = productService.getAll(parentId, childId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> postProduct(
            @Valid @RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> putProduct(@PathVariable Long id,
                                                      @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
