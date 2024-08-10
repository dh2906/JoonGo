package com.example.demo.domain.product.model;

import com.example.demo.domain.product.dto.ProductCreateRequest;
import com.example.demo.domain.product.dto.ProductUpdateRequest;
import com.example.demo.domain.category.model.Category;
import com.example.demo.domain.member.model.Member;
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

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private Long likeCount;

    @Column(name = "is_sold", nullable = false)
    private boolean isSold;


    public Product(Member member,
                   Category category,
                   ProductCreateRequest request) {
        this.seller = member;
        this.category = category;
        this.title = request.getTitle();
        this.imageUrl = request.getImageUrl();
        this.content = request.getContent();
        this.price = request.getPrice();
        this.createdDate = LocalDateTime.now();
        this.likeCount = 0L;
        this.isSold = false;
    }

    public void update(ProductUpdateRequest request, Category category) {
        if (request.getTitle() != null)
            this.title = request.getTitle();

        if (request.getCategoryId() != null)
            this.category = category;

        if (request.getImageUrl() != null)
            this.imageUrl = request.getImageUrl();

        if (request.getContent() != null)
            this.content = request.getContent();

        if (request.getPrice() != null)
            this.price = request.getPrice();
    }

    public void increaseLike() {
        likeCount++;
    }

    public void decreaseLike() {
        likeCount--;
    }

    public void updateSoldState(boolean isSold) {
        this.isSold = !isSold;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePrice(Integer price) {
        this.price = price;
    }
}
