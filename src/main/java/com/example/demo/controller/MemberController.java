package com.example.demo.controller;

import com.example.demo.annotation.SwaggerApiNoContent;
import com.example.demo.annotation.SwaggerApiOk;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.DetailMemberResponse;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    @SwaggerApiOk(summary = "전체 멤버 조회", description = "등록된 모든 멤버를 조회합니다.", implementation = List.class)
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}")
    @SwaggerApiOk(summary = "특정 멤버 조회", description = "특정 아이디의 멤버를 조회합니다.", implementation = DetailMemberResponse.class)
    public ResponseEntity<MemberResponse> getMember(@PathVariable String user_id) {
        MemberResponse response = memberService.getByUserId(user_id);

        return ResponseEntity.ok(response);
    }

//    @PostMapping
//    @SwaggerApiCreated(summary = "멤버 추가", description = "멤버를 추가합니다.", implementation = DetailMemberResponse.class)
//    public ResponseEntity<DetailMemberResponse> postMember(@Valid @RequestBody MemberCreateRequest request) {
//        DetailMemberResponse response = memberService.join(request);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    @PutMapping
    @SwaggerApiOk(summary = "정보 수정", description = "멤버의 정보를 수정합니다.", implementation = DetailMemberResponse.class)
    public ResponseEntity<DetailMemberResponse> putMember(@RequestBody MemberUpdateRequest request,
                                                          HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String userId = session.getAttribute("userId").toString();
        DetailMemberResponse response = memberService.update(userId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @SwaggerApiNoContent(summary = "회원 탈퇴", description = "회원탈퇴를 합니다.")
    public ResponseEntity<Void> deleteMember(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String userId = session.getAttribute("userId").toString();
        memberService.delete(userId);

        return ResponseEntity.noContent().build();
    }
}
