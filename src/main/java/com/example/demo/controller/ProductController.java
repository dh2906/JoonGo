package com.example.demo.controller;

import com.example.demo.annotation.SwaggerApiCreated;
import com.example.demo.annotation.SwaggerApiNoContent;
import com.example.demo.annotation.SwaggerApiOk;
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
    @SwaggerApiOk(summary = "상품 조회", description = "특정 id의 상품을 조회합니다.", implementation = ProductResponse.class)
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse response = productService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products") // 카테고리를 파라미터에 넣은 경우
    @SwaggerApiOk(summary = "상품 조회", description = "카테고리 번호로 상품을 조회합니다.", implementation = ProductResponse.class)
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @RequestParam(name = "parent", required = false) Long parentId,
            @RequestParam(name = "child", required = false) Long childId) {
        List<ProductResponse> response = productService.getAll(parentId, childId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/search")
    @SwaggerApiOk(summary = "상품 검색", description = "특정 키워드가 포함된 상품을 검색합니다.", implementation = ProductResponse.class)
    public ResponseEntity<List<ProductResponse>> getProductsBySearch(@RequestParam(name = "keyword") String keyword) {
        List<ProductResponse> responses = productService.getAllBySearch(keyword);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/products")
    @SwaggerApiCreated(summary = "상품 등록", description = "상품을 등록합니다.", implementation = ProductResponse.class)
    public ResponseEntity<ProductResponse> postProduct(
            @Valid @RequestBody ProductCreateRequest request,
            HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        ProductResponse response = productService.create(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/products/{id}")
    @SwaggerApiOk(summary = "상품 수정", description = "특정 상품의 내용을 수정합니다.", implementation = ProductResponse.class)
    public ResponseEntity<ProductResponse> putProduct(@PathVariable Long id,
                                                      @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}/toggle-sold-state")
    @SwaggerApiOk(summary = "상품 판매 상태 변경", description = "특정 상품의 판매 상태를 변경합니다.")
    public ResponseEntity<Void> toggleSoldProduct(@PathVariable Long id) {
        productService.toggleSoldState(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    @SwaggerApiNoContent(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
