package com.example.demo.controller;

import com.example.demo.annotation.SwaggerApiNoContent;
import com.example.demo.annotation.SwaggerApiOk;
import com.example.demo.controller.dto.request.ProductUpdateRequest;
import com.example.demo.controller.dto.request.ReviewCreateRequest;
import com.example.demo.controller.dto.response.ProductResponse;
import com.example.demo.controller.dto.response.ReviewResponse;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{user_id}")
    @SwaggerApiOk(summary = "리뷰 조회", description = "특정 멤버에게 작성된 리뷰들을 조회합니다.", implementation = List.class)
    public ResponseEntity<List<ReviewResponse>> getReview(@PathVariable String user_id) {
        List<ReviewResponse> response = reviewService.getAllByUserId(user_id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{user_id}")
    @SwaggerApiOk(summary = "리뷰 작성", description = "특정 멤버에게 리뷰를 작성합니다.", implementation = ReviewResponse.class)
    public ResponseEntity<ReviewResponse> postReview(@PathVariable String user_id,
                                           @RequestBody ReviewCreateRequest request,
                                           HttpSession session) {
        String authorUserId = session.getAttribute("userId").toString();

        ReviewResponse response = reviewService.create(authorUserId, user_id, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @SwaggerApiNoContent(summary = "리뷰 삭제", description = "특정 id의 리뷰를 삭제합니다.")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
