package com.example.demo.controller;

import com.example.demo.controller.dto.request.ProductCreateRequest;
import com.example.demo.controller.dto.request.ProductUpdateRequest;
import com.example.demo.controller.dto.response.ProductResponse;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

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

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductResponse>> getProductsBySearch(@RequestParam(name = "keyword") String keyword) {
        List<ProductResponse> responses = productService.getAllBySearch(keyword);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> postProduct(
            @Valid @RequestBody ProductCreateRequest request,
            HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        ProductResponse response = productService.create(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> putProduct(@PathVariable Long id,
                                                      @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}/toggle-sold-state")
    public ResponseEntity<Void> toggleSoldProduct(@PathVariable Long id) {
        productService.toggleSoldState(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
