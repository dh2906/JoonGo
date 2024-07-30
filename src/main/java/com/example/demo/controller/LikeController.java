package com.example.demo.controller;

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
    public ResponseEntity<List<LikeResponse>> like(HttpSession session) {
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
