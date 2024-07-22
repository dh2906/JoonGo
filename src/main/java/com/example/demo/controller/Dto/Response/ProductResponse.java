package com.example.demo.controller.Dto.Response;

import com.example.demo.domain.Product;
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
    private LocalDateTime createdDate;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.sellerId = product.getSeller().getId();
        this.category = product.getCategory().toString();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.imageUrl = product.getImagetUrl();
        this.price = product.getPrice();
        this.createdDate = product.getCreatedDate();
    }

    public static ProductResponse of(Product product) {
        return new ProductResponse(product);
    }
}
