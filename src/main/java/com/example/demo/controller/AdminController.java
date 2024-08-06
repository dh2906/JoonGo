package com.example.demo.controller;

import com.example.demo.annotation.SwaggerApiNoContent;
import com.example.demo.annotation.SwaggerApiOk;
import com.example.demo.controller.dto.response.DetailMemberResponse;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.service.AdminService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/members")
    @SwaggerApiOk(summary = "멤버 리스트 조회", description = "등록된 멤버들의 정보를 더 자세하게 볼 수 있습니다.", implementation = List.class)
    public ResponseEntity<List<DetailMemberResponse>> getMembers() {
        List<DetailMemberResponse> response = adminService.getAllMembers();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{user_id}")
    @SwaggerApiNoContent(summary = "멤버 강제 삭제", description = "멤버를 강제 삭제할 수 있습니다.")
    public ResponseEntity<Void> deleteMember(@PathVariable String userId) {
        adminService.deleteMember(userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    @SwaggerApiNoContent(summary = "상품 강제 삭제", description = "상품을 강제 삭제할 수 있습니다.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reviews/{id}")
    @SwaggerApiNoContent(summary = "리뷰 강제 삭제", description = "리뷰를 강제 삭제할 수 있습니다.")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        adminService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }
}
