package com.example.demo.controller;

import com.example.demo.controller.Dto.Request.ArticleCreateRequest;
import com.example.demo.controller.Dto.Response.ArticleResponse;
import com.example.demo.domain.Article;
import com.example.demo.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        ArticleResponse response = new ArticleResponse(articleService.getById(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> postArticle(@RequestBody ArticleCreateRequest articleCreateRequest) {
        System.out.println("123");
        ArticleResponse response = articleService.create(articleCreateRequest);
        return ResponseEntity.created(URI.create("/" + response.getId())).body(response);
    }
}
