package com.example.demo.controller;

import com.example.demo.controller.Dto.Request.ProductCreateRequest;
import com.example.demo.controller.Dto.Response.ProductResponse;
import com.example.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse response = new ProductResponse(productService.getById(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products") // 카테고리를 파라미터에 넣은 경우
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @RequestParam Long parentId, @RequestParam(required = false) Long childId) {
        List<ProductResponse> response = productService.getAllByCategory(parentId, childId);
        return response.stream().map(ProductResponse::of).toList();
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> postProduct(
            @RequestBody ProductCreateRequest productCreateRequest,
            @RequestParam Long parentId, @RequestParam Long childId ) {
        System.out.println("123");
        ProductResponse response = productService.create(productCreateRequest);
        return ResponseEntity.created(URI.create("/" + response.getId())).body(response);
    }
}
