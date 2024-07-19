package domain;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String user_id;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String phone_number;

    @Column(length = 100, nullable = false)
    private String account_number;

    @OneToMany(mappedBy = "author")
    private List<Article> articles = new ArrayList<>();
}
