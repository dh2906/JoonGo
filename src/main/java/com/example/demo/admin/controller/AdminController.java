package com.example.demo.admin.controller;

import com.example.demo.domain.member.dto.DetailMemberResponse;
import com.example.demo.domain.review.dto.DetailReviewResponse;
import com.example.demo.global.except.ExceptionGenerator;
import com.example.demo.global.except.StatusEnum;
import com.example.demo.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾지 못한 경우")
            }
    )
    @Operation(summary = "전체 멤버 조회")
    @GetMapping("/members")
    public ResponseEntity<List<DetailMemberResponse>> getMembers() {
        List<DetailMemberResponse> response = adminService.getAllMembers();

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "멤버 강제 삭제")
    @DeleteMapping("/members/{user_id}")
    public ResponseEntity<Void> deleteMember(@PathVariable String user_id) {
        adminService.deleteMember(user_id);

        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "상품 강제 삭제")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "리뷰를 찾지 못한 경우")
            }
    )
    @Operation(summary = "리뷰 전체 조회")
    @GetMapping("/reviews")
    public ResponseEntity<List<DetailReviewResponse>> getReviews() {
        List<DetailReviewResponse> response = adminService.getAllReviews();

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "리뷰를 찾지 못한 경우")
            }
    )
    @Operation(summary = "리뷰 강제 삭제")
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        adminService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾지 못한 경우"),
                    @ApiResponse(responseCode = "400", description = "쿼리 파라미터에 값을 넣지 않은 경우")
            }
    )
    @Operation(summary = "멤버 활동 정지")
    @PostMapping("/members/{user_id}/suspend")
    public ResponseEntity<String> suspendMember(@PathVariable String user_id,
                                            @RequestParam(defaultValue = "0") int year,
                                            @RequestParam(defaultValue = "0") int month,
                                            @RequestParam(defaultValue = "0") int day,
                                            @RequestParam(defaultValue = "0") int hour,
                                            @RequestParam(defaultValue = "0") int minute) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime endTime = now
                .plusYears(year)
                .plusMonths(month)
                .plusDays(day)
                .plusHours(hour)
                .plusMinutes(minute);

        if (endTime.isEqual(now)) {
            throw new ExceptionGenerator(StatusEnum.CONTAIN_EMPTY_REQUEST);
        }

        adminService.suspendMember(user_id, endTime);

        return ResponseEntity.ok().body(user_id + "님의 활동 정지 처리가 완료되었습니다.");
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾지 못한 경우")
            }
    )
    @Operation(summary = "멤버 활동 정지 해제")
    @PostMapping("/members/{user_id}/unsuspend")
    public ResponseEntity<String> unsuspendMember(@PathVariable String user_id) {
        adminService.unsuspendMember(user_id);

        return ResponseEntity.ok().body(user_id + "님의 활동 정지 해제 처리가 완료되었습니다.");
    }
}
