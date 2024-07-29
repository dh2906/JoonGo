package com.example.demo.controller;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody MemberCreateRequest request) {
        authService.register(request);

        System.out.println("회원가입 성공");
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn(HttpServletResponse httpServletResponse, @RequestBody LogInRequest request) {
        authService.logIn(request);
        System.out.println("로그인 성공");

        return ResponseEntity.status(200).build();
    }
}
