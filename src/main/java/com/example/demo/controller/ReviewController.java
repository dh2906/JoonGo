package com.example.demo.controller;

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
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getReview(@RequestParam(name = "user_id") String sellerUserId) {
        List<ReviewResponse> response = reviewService.getAllByUserId(sellerUserId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponse> postReview(@RequestParam(name = "user_id") String sellerUserId,
                                           @RequestBody ReviewCreateRequest request,
                                           HttpSession session) {
        String authorUserId = session.getAttribute("userId").toString();

        ReviewResponse response = reviewService.create(authorUserId, sellerUserId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
