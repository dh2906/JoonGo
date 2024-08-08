package com.example.demo.interceptor;

import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) // 세션이 없는지 검사
            throw new ExceptionGenerator(StatusEnum.SESSION_EXPIRED);

        return true;
    }
}
