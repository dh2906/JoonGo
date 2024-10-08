package com.example.demo.domain.product.service;

import com.example.demo.domain.product.dto.ProductCreateRequest;
import com.example.demo.domain.product.dto.ProductUpdateRequest;
import com.example.demo.domain.product.dto.ProductResponse;
import com.example.demo.domain.product.model.Product;
import com.example.demo.domain.category.model.Category;
import com.example.demo.domain.member.model.Member;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.domain.product.repository.ProductRepository;
import com.example.demo.domain.category.repository.CategoryRepository;
import com.example.demo.domain.member.repository.MemberRepository;
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
                                           .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_PRODUCT));
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
            throw new ExceptionGenerator(StatusEnum.NOT_PRESENT_PRODUCT);

        return products.stream().map(ProductResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllByKeyword(String keyword) {
        List<Product> products = productRepository.findAllByTitleContainsIgnoreCaseOrContentContainsIgnoreCase(keyword, keyword);

        if (products.isEmpty())
            throw new ExceptionGenerator(StatusEnum.NOT_PRESENT_PRODUCT);

        return products.stream().map(ProductResponse::of).toList();
    }

    @Transactional
    public ProductResponse create(String userId, ProductCreateRequest request) {
        if (request.checkContainNull())
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);

        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_MEMBER));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_CATEGORY));

        Product product = new Product(member, category, request);

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        if (request.checkIsEmpty())
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);

        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_PRODUCT));

        Category category;

        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                                         .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_CATEGORY));
            product.updateCategory(category);
        }

        if (request.getTitle() != null)
            product.updateTitle(request.getTitle());

        if (request.getContent() != null)
            product.updateContent(request.getContent());

        if (request.getPrice() != null)
            product.updatePrice(request.getPrice());

        if (request.getImageUrl() != null)
            product.updateImageUrl(request.getImageUrl());

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
                                           .orElseThrow(() -> new ExceptionGenerator(StatusEnum.NOT_PRESENT_PRODUCT));

        product.updateSoldState(product.isSold()); // 요청 내용대로 판매 상태 변환
    }
}
