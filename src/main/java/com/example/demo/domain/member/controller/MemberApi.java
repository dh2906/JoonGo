package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.DetailMemberResponse;
import com.example.demo.domain.member.dto.MemberResponse;
import com.example.demo.domain.member.dto.MemberUpdateRequest;
import com.example.demo.global.except.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface MemberApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    @Operation(summary = "전체 멤버 조회")
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers();

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    @Operation(summary = "특정 멤버 조회")
    @GetMapping("/{user_id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable String user_id);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "요청값이 비어있는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            }
    )
    @Operation(summary = "멤버 정보 수정", description = "변경을 원하는 값만 요청에 넣어도 됩니다.")
    @PutMapping
    public ResponseEntity<DetailMemberResponse> putMember(@RequestBody MemberUpdateRequest request,
                                                          HttpServletRequest httpServletRequest);

    @ApiResponse(responseCode = "204")
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(HttpServletRequest httpServletRequest);
}
