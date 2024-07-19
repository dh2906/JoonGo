package com.example.demo.service;

import com.example.demo.controller.Dto.Request.ArticleCreateRequest;
import com.example.demo.controller.Dto.Response.ArticleResponse;
import com.example.demo.domain.Article;
import com.example.demo.domain.Category;
import com.example.demo.domain.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public ArticleService(ArticleRepository articleRepository,
                          MemberRepository memberRepository,
                          CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    public Article getById(Long id) {
        return articleRepository.findById(id).get();
    }

    public ArticleResponse create(ArticleCreateRequest request) {
        Member member = memberRepository.findById(request.getSellerId()).get();
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        Article article = new Article(member, category, request);

        return ArticleResponse.of(article);
    }
}
