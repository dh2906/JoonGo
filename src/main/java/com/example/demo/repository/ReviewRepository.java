package com.example.demo.repository;

import com.example.demo.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    //@Query("SELECT r FROM Review r WHERE r.seller.userId = :sellerUserId")
    List<Review> findAllBySellerUserId(String serllerUserId);
}
