package com.example.demo.controller;

import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.DetailMemberResponse;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "전체 멤버 조회")
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우")
            }
    )
    @Operation(summary = "특정 멤버 조회")
    @GetMapping("/{user_id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable String user_id) {
        MemberResponse response = memberService.getByUserId(user_id);

        return ResponseEntity.ok(response);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "요청값이 비어있는 경우"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우"),
            }
    )
    @Operation(summary = "멤버 정보 수정", description = "변경을 원하는 값만 요청에 넣어도 됩니다.")
    @PutMapping
    public ResponseEntity<DetailMemberResponse> putMember(@RequestBody MemberUpdateRequest request,
                                                          HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String userId = session.getAttribute("userId").toString();
        DetailMemberResponse response = memberService.update(userId, request);

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String userId = session.getAttribute("userId").toString();
        memberService.delete(userId);

        return ResponseEntity.noContent().build();
    }
}
