package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.MemberUpdateRequest;
import com.example.demo.domain.member.dto.DetailMemberResponse;
import com.example.demo.domain.member.dto.MemberResponse;
import com.example.demo.domain.member.service.MemberService;
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
public class MemberController implements MemberApi{
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable String user_id) {
        MemberResponse response = memberService.getByUserId(user_id);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<DetailMemberResponse> putMember(@RequestBody MemberUpdateRequest request,
                                                          HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String userId = session.getAttribute("userId").toString();
        DetailMemberResponse response = memberService.update(userId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String userId = session.getAttribute("userId").toString();
        memberService.delete(userId);

        return ResponseEntity.noContent().build();
    }
}
