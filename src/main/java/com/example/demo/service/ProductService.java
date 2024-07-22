package com.example.demo.service;

import com.example.demo.controller.Dto.Request.ProductCreateRequest;
import com.example.demo.controller.Dto.Response.ProductResponse;
import com.example.demo.domain.Product;
import com.example.demo.domain.Category;
import com.example.demo.domain.Member;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          MemberRepository memberRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    public List<ProductResponse> getAllByCategory(Long parentId, Long childId) {
        if (childId != null) {
            productRepository.findAllByChildCategoryId(childId);
        }
    }

    public ProductResponse create(ProductCreateRequest request) {
        Member member = memberRepository.findById(request.getSellerId()).get();
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        Product product = new Product(member, category, request);

        return ProductResponse.of(product);
    }
}
