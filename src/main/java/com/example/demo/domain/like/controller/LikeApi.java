package com.example.demo.domain.like.controller;

import com.example.demo.domain.like.dto.LikeResponse;
import com.example.demo.global.except.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface LikeApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "좋아요를 누른 상품을 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    @Operation(summary = "좋아요 누른 상품 조회")
    @GetMapping("/likes")
    public ResponseEntity<List<LikeResponse>> getLikeByUserId(HttpSession session);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    @Operation(summary = "좋아요", description = "특정 상품에 좋아요를 누릅니다. 이미 좋아요가 눌린 상황이라면 해제합니다.")
    @PutMapping("/likes/{productId}")
    public ResponseEntity<Void> postLike(@PathVariable Long productId, HttpSession session);
}
