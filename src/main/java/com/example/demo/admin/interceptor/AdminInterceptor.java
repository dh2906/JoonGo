package com.example.demo.admin.interceptor;

import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.domain.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {
    private final ProductService productService;
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null)
            throw new ExceptionGenerator(StatusEnum.SESSION_EXPIRED);


        if (!session.getAttribute("role").equals("admin"))
            throw new ExceptionGenerator(StatusEnum.PERMISSION_ERROR);

        return true;
    }
}
