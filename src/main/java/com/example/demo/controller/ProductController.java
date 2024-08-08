package com.example.demo.controller;

import com.example.demo.controller.dto.request.ProductCreateRequest;
import com.example.demo.controller.dto.request.ProductUpdateRequest;
import com.example.demo.controller.dto.response.ProductResponse;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "상품을 찾지 못한 경우")
            }
    )
    @Operation(summary = "특정 상품 조회")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse response = productService.getById(id);

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "상품을 찾지 못한 경우")
            }
    )
    @Operation(summary = "카테고리로 상품 조회")
    @GetMapping("/products") // 카테고리를 파라미터에 넣은 경우
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @RequestParam(name = "parent", required = false) Long parentId,
            @RequestParam(name = "child", required = false) Long childId) {
        List<ProductResponse> response = productService.getAll(parentId, childId);

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "상품을 찾지 못한 경우")
            }
    )
    @Operation(summary = "키워드로 상품 조회")
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductResponse>> getProductsByKeyword(@RequestParam(name = "keyword") String keyword) {
        List<ProductResponse> responses = productService.getAllByKeyword(keyword);

        return ResponseEntity.ok(responses);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "요청에 빈 값이 존재하는 경우"),
                    @ApiResponse(responseCode = "404", description = "세션에 저장된 값으로 유저를 찾을 수 없는 경우"),
                    @ApiResponse(responseCode = "404", description = "해당 카테고리 아이디를 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "상품 등록")
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> postProduct(
            @Valid @RequestBody ProductCreateRequest request,
            HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        ProductResponse response = productService.create(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "요청에 빈 값이 존재하는 경우"),
                    @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없는 경우"),
                    @ApiResponse(responseCode = "404", description = "해당 카테고리 아이디를 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "특정 상품 내용 수정", description = "변경하고 싶은 값만 요청값에 넣어도 됩니다.")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> putProduct(@PathVariable Long id,
                                                      @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "특정 상품 판매 상태 변경")
    @PutMapping("/products/{id}/toggle-sold-state")
    public ResponseEntity<Void> toggleSoldProduct(@PathVariable Long id) {
        productService.toggleSoldState(id);

        return ResponseEntity.ok().build();
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "특정 상품 삭제")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
