package com.example.demo.controller;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.LogInRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "요청에 빈 값이 존재하는 경우"),
                    @ApiResponse(responseCode = "409", description = "아이디가 중복되는 경우")
            }
    )
    @Operation(summary = "회원가입")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody MemberCreateRequest request) {
        authService.register(request);

        return ResponseEntity.status(201).body("회원가입 성공");
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "해당 계정을 찾을 수 없는 경우"),
                    @ApiResponse(responseCode = "401", description = "로그인에 실패한 경우"),
                    @ApiResponse(responseCode = "403", description = "해당 계정이 활동 정지를 당한 경우")
            }
    )
    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> logIn(HttpServletRequest httpServletRequest, @RequestBody LogInRequest request) {
        authService.logIn(request);

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", request.getUserId());
        session.setAttribute("role", memberService.getByUserId(request.getUserId()).getRole());
        session.setMaxInactiveInterval(3600);

        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    @ApiResponse(responseCode = "200")
    @Operation(summary = "로그아웃")
    public ResponseEntity<String> logOut(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null)
            return ResponseEntity.ok("이미 로그아웃 상태입니다.");

        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
