package com.example.demo.controller;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> register(@Valid @RequestBody MemberCreateRequest request) {
        authService.register(request);

        return ResponseEntity.status(201).body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(HttpServletRequest httpServletRequest, @RequestBody LogInRequest request) {
        authService.logIn(request);

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", request.getUserId());
        session.setMaxInactiveInterval(1800);

        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null)
            return ResponseEntity.ok("이미 로그아웃 상태입니다.");

        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
