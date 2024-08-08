package com.example.demo.controller;

import com.example.demo.controller.dto.request.ReviewCreateRequest;
import com.example.demo.controller.dto.response.ReviewResponse;
import com.example.demo.service.ReviewService;
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
public class ReviewController {
    private final ReviewService reviewService;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우"),
            }
    )
    @Operation(summary = "특정 멤버에게 작성된 리뷰 조회")
    @GetMapping("/{user_id}")
    public ResponseEntity<List<ReviewResponse>> getReview(@PathVariable String user_id) {
        List<ReviewResponse> response = reviewService.getAllByUserId(user_id);

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "요청에 빈 값이 존재하는 경우"),
                    @ApiResponse(responseCode = "400", description = "줄 수 있는 점수(0 ~ 10점)의 범위를 벗어나는 경우"),
                    @ApiResponse(responseCode = "404", description = "작성자를 찾을 수 없는 경우"),
                    @ApiResponse(responseCode = "404", description = "판매자를 찾을 수 없는 경우"),
            }
    )
    @Operation(summary = "리뷰 작성")
    @PostMapping("/{user_id}")
    public ResponseEntity<ReviewResponse> postReview(@PathVariable String user_id,
                                           @RequestBody ReviewCreateRequest request,
                                           HttpSession session) {
        String authorUserId = session.getAttribute("userId").toString();

        ReviewResponse response = reviewService.create(authorUserId, user_id, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
