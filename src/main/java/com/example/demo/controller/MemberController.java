package com.example.demo.controller;

import com.example.demo.controller.Dto.Request.MemberCreateRequest;
import com.example.demo.controller.Dto.Request.MemberUpdateRequest;
import com.example.demo.controller.Dto.Response.MemberResponse;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        MemberResponse response = memberService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> postMember(@Valid @RequestBody MemberCreateRequest request) {
        MemberResponse response = memberService.join(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> putMember(@PathVariable Long id,
                                                    @RequestBody MemberUpdateRequest request) {
        MemberResponse response = memberService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
