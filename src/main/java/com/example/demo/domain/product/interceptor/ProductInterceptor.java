package com.example.demo.domain.product.interceptor;

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
public class ProductInterceptor implements HandlerInterceptor {
    private final ProductService productService;
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) // GET 요청은 검사 X
            return true;

        HttpSession session = request.getSession(false);

        if (session == null) // 세션이 없는지 검사
            throw new ExceptionGenerator(StatusEnum.SESSION_EXPIRED);

        if ("POST".equalsIgnoreCase(request.getMethod())) { // POST 요청은 세션에 userId 값이 존재하는 지 검사
            if (session.getAttribute("userId") == null)
                throw new ExceptionGenerator(StatusEnum.PERMISSION_ERROR);

            return true;
        }

        // PUT, DELETE 요청에 대한 처리

        String uri = request.getRequestURI();
        String[] uriParts = uri.split("/");
        Long productId = Long.valueOf(uriParts[2]);


        String userId = memberService.getById(productService.getById(productId).getSellerId()).getUserId();

        if (!userId.equals(session.getAttribute("userId").toString())) // 게시물 작성자와 요청자가 다른 경우
            throw new ExceptionGenerator(StatusEnum.PERMISSION_ERROR);

        return true;
    }
}
