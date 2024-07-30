package com.example.demo.repository;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.category.id = :childId AND p.category.parentCategory.id = :parentId")
    List<Product> findAllByParentAndChildCategory(@Param("parentId") Long parentId, @Param("childId") Long childId);

    @Query("SELECT p FROM Product p WHERE p.category.parentCategory.id = :parentId")
    List<Product> findAllByParentCategory(@Param("parentId") Long parentId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :childId")
    List<Product> findAllByChildCategory(@Param("childId") Long childId);

    List<Product> findAllByTitleContainsIgnoreCaseOrContentContainsIgnoreCase(String title, String content);
}
