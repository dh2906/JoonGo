package com.example.demo.controller;

import com.example.demo.controller.dto.response.LikeResponse;
import com.example.demo.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "좋아요를 누른 상품을 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "좋아요 누른 상품 조회")
    @GetMapping("/likes")
    public ResponseEntity<List<LikeResponse>> like(HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        List<LikeResponse> response = likeService.getByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우"),
                    @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "좋아요", description = "특정 상품에 좋아요를 누릅니다. 이미 좋아요가 눌린 상황이라면 해제합니다.")
    @PutMapping("/likes/{productId}")
    public ResponseEntity<Void> postLike(@PathVariable Long productId, HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        likeService.toggleLike(userId, productId);

        return ResponseEntity.ok().build();
    }
}
