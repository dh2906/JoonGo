package com.example.demo.interceptor;

import com.example.demo.domain.Member;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.service.MemberService;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MemberInterceptor implements HandlerInterceptor {
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) // GET 요청은 검사 X
            return true;

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) // PUT, DELETE 요청에서 세션이 없는지 검사
            throw new ExceptionGenerator(StatusEnum.SESSION_EXPIRED);

        return true;
    }
}
