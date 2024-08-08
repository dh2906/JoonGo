package com.example.demo.interceptor;

import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ReviewInterceptor implements HandlerInterceptor {
    private final MemberService memberService;
    private final ReviewService reviewService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) // GET 요청은 검사 X
            return true;

        HttpSession session = request.getSession(false);

        if (session == null) // 세션이 없는지 검사
            throw new ExceptionGenerator(StatusEnum.SESSION_EXPIRED);

        if ("POST".equalsIgnoreCase(request.getMethod()))
            return true;

        String uri = request.getRequestURI();
        String[] uriParts = uri.split("/");
        Long reviewId = Long.valueOf(uriParts[2]);
        String userId = memberService.getById(reviewService.getById(reviewId).getAuthor().getId()).getUserId();

        if (!userId.equals(session.getAttribute("userId").toString()))
            throw new ExceptionGenerator(StatusEnum.PERMISSION_ERROR);

        return true;
    }
}