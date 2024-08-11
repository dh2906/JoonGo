package com.example.demo.domain.review.controller;

import com.example.demo.domain.review.dto.ReviewCreateRequest;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.dto.ReviewUpdateRequest;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ReviewApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            }
    )
    @Operation(summary = "특정 멤버에게 작성된 리뷰 조회")
    @GetMapping("/{user_id}")
    public ResponseEntity<List<ReviewResponse>> getReview(@PathVariable String user_id);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "요청에 빈 값이 존재하는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "줄 수 있는 점수(0 ~ 10점)의 범위를 벗어나는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "작성자를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "판매자를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            }
    )
    @Operation(summary = "리뷰 작성")
    @PostMapping("/{user_id}")
    public ResponseEntity<ReviewResponse> postReview(@PathVariable String user_id,
                                                     @RequestBody ReviewCreateRequest request,
                                                     HttpSession session);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "요청에 빈 값이 존재하는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "줄 수 있는 점수(0 ~ 10점)의 범위를 벗어나는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "해당 리뷰를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    @Operation(summary = "리뷰 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> putReview(@PathVariable Long id,
                                                     @RequestBody ReviewUpdateRequest request,
                                                     HttpSession session);

    @ApiResponse(responseCode = "204")
    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id);
}
