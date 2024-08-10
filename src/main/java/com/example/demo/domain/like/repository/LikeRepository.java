package com.example.demo.domain.like.repository;

import com.example.demo.domain.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    //@Query(value = "SELECT COUNT(l.id) > 0 FROM Like l WHERE l.member.userId = :userId and l.product.id = :productId")
    boolean existsByMemberUserIdAndProductId(String userId, Long productId);

    //@Query("SELECT l FROM Like l WHERE l.member.userId = :userId")
    List<Like> findAllByMemberUserId(String userId);

    //@Query("DELETE FROM Like l WHERE l.member.userId = :userId and l.product.id = :productId")
    void deleteByMemberUserIdAndProductId(String userId, Long productId);
}
