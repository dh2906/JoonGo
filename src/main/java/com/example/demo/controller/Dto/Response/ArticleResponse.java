package com.example.demo.controller.Dto.Response;

import com.example.demo.domain.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponse {
    private Long id;
    private Long sellerId;
    private String category;
    private String title;
    private String content;
    private String imageUrl;
    private Integer price;
    private LocalDateTime createdDate;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.sellerId = article.getSeller().getId();
        this.category = article.getCategory().toString();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageUrl = article.getImagetUrl();
        this.price = article.getPrice();
        this.createdDate = article.getCreatedDate();
    }

    public static ArticleResponse of(Article article) {
        return new ArticleResponse(article);
    }
}