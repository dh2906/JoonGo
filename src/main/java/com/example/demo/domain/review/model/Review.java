package com.example.demo.domain.review.model;

import com.example.demo.domain.review.dto.ReviewCreateRequest;
import com.example.demo.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @Column(length = 1000)
    private String content;

    @Column
    private int score;

    public Review(Member author, Member seller, ReviewCreateRequest request) {
        this.author = author;
        this.seller = seller;
        this.content = request.getContent();
        this.score = request.getScore();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateScore(int score) {
        this.score = score;
    }
}
