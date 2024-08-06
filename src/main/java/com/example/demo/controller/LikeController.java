package com.example.demo.controller;

import com.example.demo.annotation.SwaggerApiOk;
import com.example.demo.controller.dto.response.LikeResponse;
import com.example.demo.service.LikeService;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/likes")
    @SwaggerApiOk(summary = "좋아요 누른 상품 조회", description = "요청을 보낸 멤버가 좋아요를 누른 상품을 조회합니다.", implementation = List.class)
    public ResponseEntity<List<LikeResponse>> like(HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        List<LikeResponse> response = likeService.getByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/likes/{productId}")
    @SwaggerApiOk(summary = "좋아요", description = "멤버가 특정 상품에 좋아요를 누릅니다. 이미 좋아요가 눌린 상황이라면 해제합니다.")
    public ResponseEntity<Void> postLike(@PathVariable Long productId, HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        likeService.toggleLike(userId, productId);

        return ResponseEntity.ok().build();
    }
}
