package com.example.demo.global.config;

import com.example.demo.admin.interceptor.AdminInterceptor;
import com.example.demo.domain.member.interceptor.MemberInterceptor;
import com.example.demo.domain.product.interceptor.ProductInterceptor;
import com.example.demo.domain.review.interceptor.ReviewInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final ProductInterceptor productInterceptor;
    private final ReviewInterceptor reviewInterceptor;
    private final MemberInterceptor memberInterceptor;
    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productInterceptor)
                .addPathPatterns("/products/**");

        registry.addInterceptor(reviewInterceptor)
                .addPathPatterns("/reviews/**");

        registry.addInterceptor(memberInterceptor)
                .addPathPatterns("/members/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }
}
