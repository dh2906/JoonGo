package com.example.demo.domain;

import com.example.demo.controller.Dto.Request.MemberCreateRequest;
import com.example.demo.controller.Dto.Request.MemberUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "seller", orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Member(MemberCreateRequest request) {
        this.userId = request.getUserId();
        this.password = request.getPassword();
        this.phoneNumber = request.getPhoneNumber();
        this.account = request.getAccount();
    }

    public void update(MemberUpdateRequest request) {
        if (request.getPassword() != null)
            this.password = request.getPassword();

        if (request.getPhoneNumber() != null)
            this.phoneNumber = request.getPhoneNumber();

        if (request.getAccount() != null)
            this.account = request.getAccount();
    }
}
