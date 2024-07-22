package com.example.demo.domain;

import com.example.demo.controller.Dto.Request.ProductCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300)
    private String imagetUrl;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    private LocalDateTime createdDate;

    public Product(Member member,
                   Category category,
                   ProductCreateRequest request) {
        this.seller = member;
        this.category = category;
        this.title = request.getTitle();
        this.imagetUrl = request.getImageUrl();
        this.content = request.getContent();
        this.price = request.getPrice();
        this.createdDate = LocalDateTime.now();
    }
}
