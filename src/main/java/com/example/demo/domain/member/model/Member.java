package com.example.demo.domain.member.model;

import com.example.demo.global.auth.RegisterRequest;
import com.example.demo.domain.product.model.Product;
import com.example.demo.domain.review.model.Review;
import com.example.demo.domain.like.model.Like;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String role;

    @Column(name = "is_suspend", nullable = false)
    private boolean isSuspend;

    @Column(name = "suspension_end_date")
    private LocalDateTime suspensionEndDate;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Member(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.role = "user";
        this.isSuspend = false;
    }

    public Member(RegisterRequest request) {
        this.userId = request.getUserId();
        this.password = request.getPassword();
        this.role = "user";
        this.isSuspend = false;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void suspend(LocalDateTime endTime) {
        this.isSuspend = true;
        this.suspensionEndDate = endTime;
    }

    public void unsuspend() {
        this.isSuspend = false;
        this.suspensionEndDate = null;
    }
}
