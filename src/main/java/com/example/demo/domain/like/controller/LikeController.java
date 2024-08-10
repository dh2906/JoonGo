package com.example.demo.domain.like.controller;

import com.example.demo.domain.like.dto.LikeResponse;
import com.example.demo.domain.like.service.LikeService;
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
public class LikeController implements LikeApi {
    private final LikeService likeService;

    @GetMapping("/likes")
    public ResponseEntity<List<LikeResponse>> getLikeByUserId(HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        List<LikeResponse> response = likeService.getByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/likes/{productId}")
    public ResponseEntity<Void> postLike(@PathVariable Long productId, HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        likeService.toggleLike(userId, productId);

        return ResponseEntity.ok().build();
    }
}
