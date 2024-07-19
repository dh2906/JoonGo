package domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300)
    private String productUrl;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    private LocalDateTime createdDate;
}
