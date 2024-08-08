package com.example.demo.domain;

import com.example.demo.controller.dto.request.MemberCreateRequest;
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

    @Column(name = "phone_number", length = 100, nullable = false)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String account;

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

    public Member(String userId, String password, String phoneNumber, String account) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.role = "user";
        this.isSuspend = false;
    }

    public Member(MemberCreateRequest request) {
        this.userId = request.getUserId();
        this.password = request.getPassword();
        this.phoneNumber = request.getPhoneNumber();
        this.account = request.getAccount();
        this.role = "user";
        this.isSuspend = false;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void udpateAccount(String account) {
        this.account = account;
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
