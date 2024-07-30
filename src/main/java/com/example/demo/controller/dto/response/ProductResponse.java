package com.example.demo.controller.dto.response;

import com.example.demo.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductResponse {
    private Long id;
    private Long sellerId;
    private String category;
    private String title;
    private String content;
    private String imageUrl;
    private Integer price;
    private Long likeCount;
    private boolean isSold;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.sellerId = product.getSeller().getId();
        this.category = product.getCategory().toString();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.imageUrl = product.getImageUrl();
        this.price = product.getPrice();
        this.createdDate = product.getCreatedDate();
        this.isSold = product.isSold();
        this.likeCount = product.getLikeCount();
    }

    public static ProductResponse of(Product product) {
        return new ProductResponse(product);
    }
}
