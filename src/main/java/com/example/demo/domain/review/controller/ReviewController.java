package com.example.demo.domain.review.controller;

import com.example.demo.domain.review.dto.ReviewCreateRequest;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewApi{
    private final ReviewService reviewService;

    @GetMapping("/{user_id}")
    public ResponseEntity<List<ReviewResponse>> getReview(@PathVariable String user_id) {
        List<ReviewResponse> response = reviewService.getAllByUserId(user_id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<ReviewResponse> postReview(@PathVariable String user_id,
                                           @RequestBody ReviewCreateRequest request,
                                           HttpSession session) {
        String authorUserId = session.getAttribute("userId").toString();

        ReviewResponse response = reviewService.create(authorUserId, user_id, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
