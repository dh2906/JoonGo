package com.example.demo.service;

import com.example.demo.controller.dto.request.ProductCreateRequest;
import com.example.demo.controller.dto.request.ProductUpdateRequest;
import com.example.demo.controller.dto.response.ProductResponse;
import com.example.demo.domain.Product;
import com.example.demo.domain.Category;
import com.example.demo.domain.Member;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_PRODUCT));
        return ProductResponse.of(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAll(Long parentId, Long childId) {
        List<Product> products;

        if (parentId == null && childId == null)
            products = productRepository.findAll();

        else if (parentId == null && childId != null)
            products = productRepository.findAllByChildCategory(childId);

        else if (parentId != null && childId == null)
            products = productRepository.findAllByParentCategory(parentId);

        else
            products = productRepository.findAllByParentAndChildCategory(parentId, childId);

        if (products.isEmpty())
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_PRODUCT);

        return products.stream().map(ProductResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllBySearch(String keyword) {
        List<Product> products = productRepository.findAllByTitleContainsIgnoreCaseOrContentContainsIgnoreCase(keyword, keyword);

        return products.stream().map(ProductResponse::of).toList();
    }

    @Transactional
    public ProductResponse create(String userId, ProductCreateRequest request) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_CATEGORY));

        Product product = new Product(member, category, request);

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_PRODUCT));

        Category category;

        if (request.getCategoryId() != null)
            category = categoryRepository.findById(request.getCategoryId())
                                         .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_CATEGORY));

        else
            category = productRepository.findById(id)
                                        .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_CATEGORY))
                                        .getCategory();

        product.update(request, category);

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void toggleSoldState(Long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_PRODUCT));

        product.updateSoldState(product.isSold()); // 요청 내용대로 판매 상태 변환
    }
}
