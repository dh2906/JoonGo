package com.example.demo.controller;

import com.example.demo.annotation.SwaggerApiNoContent;
import com.example.demo.annotation.SwaggerApiOk;
import com.example.demo.controller.dto.response.DetailMemberResponse;
import com.example.demo.controller.dto.response.DetailReviewResponse;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.service.AdminService;
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

    @GetMapping("/members")
    @SwaggerApiOk(summary = "멤버 리스트 조회", description = "등록된 멤버들의 정보를 더 자세하게 볼 수 있습니다.", implementation = List.class)
    public ResponseEntity<List<DetailMemberResponse>> getMembers() {
        List<DetailMemberResponse> response = adminService.getAllMembers();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{user_id}")
    @SwaggerApiNoContent(summary = "멤버 강제 삭제", description = "멤버를 강제 삭제할 수 있습니다.")
    public ResponseEntity<Void> deleteMember(@PathVariable String user_id) {
        adminService.deleteMember(user_id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    @SwaggerApiNoContent(summary = "상품 강제 삭제", description = "상품을 강제 삭제할 수 있습니다.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reviews")
    @SwaggerApiOk(summary = "리뷰 전체 조회", description = "현재 작성된 모든 리뷰를 조회할 수 있습니다.")
    public ResponseEntity<List<DetailReviewResponse>> getReviews() {
        List<DetailReviewResponse> response = adminService.getAllReviews();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reviews/{id}")
    @SwaggerApiNoContent(summary = "리뷰 강제 삭제", description = "리뷰를 강제 삭제할 수 있습니다.")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        adminService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/members/{user_id}/suspend")
    @SwaggerApiOk(summary = "멤버 활동 정지", description = "특정 멤버의 활동을 일시정지 시킬 수 있습니다.")
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

    @PostMapping("/members/{user_id}/unsuspend")
    @SwaggerApiOk(summary = "멤버 활동 정지 해제", description = "특정 멤버의 활동을 일시정지를 해제 시킬 수 있습니다.")
    public ResponseEntity<String> unsuspendMember(@PathVariable String user_id) {
        adminService.unsuspendMember(user_id);

        return ResponseEntity.ok().body(user_id + "님의 활동 정지 해제 처리가 완료되었습니다.");
    }
}
