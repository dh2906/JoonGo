package com.example.demo.service;

import com.example.demo.controller.Dto.Request.ProductCreateRequest;
import com.example.demo.controller.Dto.Request.ProductUpdateRequest;
import com.example.demo.controller.Dto.Response.MemberResponse;
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

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id).get();
        return ProductResponse.of(product);
    }

    public List<ProductResponse> getAll(Long parentId, Long childId) {
        List<Product> products;

        if (parentId == null && childId == null)
            products = productRepository.findAll();

        else if (parentId == null && childId != null)
            products = productRepository.findAllByChildCategory(childId).get();

        else if (parentId != null && childId == null)
            products = productRepository.findAllByParentCategory(parentId).get();

        else
            products = productRepository.findAllByParentAndChildCategory(parentId, childId).get();

        return products.stream().map(ProductResponse::of).toList();
    }

    public ProductResponse create(ProductCreateRequest request) {
        Member member = memberRepository.findById(request.getSellerId()).get();
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        Product product = new Product(member, category, request);

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).get();
        Category category = categoryRepository.findById(request.getCategoryId()).get();

        product.update(request, category);

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
