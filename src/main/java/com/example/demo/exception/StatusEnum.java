package com.example.demo.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {
    NOT_PRESENT_MEMBER(404, "Not Found", "멤버가 존재하지 않습니다."),
    NOT_PRESENT_AUTHOR(404, "Not Found", "작성자가 존재하지 않습니다."),
    NOT_PRESENT_SELLER(404, "Not Found", "판매자가 존재하지 않습니다."),
    NOT_PRESENT_PRODUCT(404, "Not Found", "상품이 존재하지 않습니다."),
    NOT_PRESENT_REVIEW(404, "Not Found", "리뷰가 존재하지 않습니다."),
    NOT_PRESENT_LIKE(404, "Not Found", "좋아요를 누른 상품이 존재하지 않습니다."),
    NOT_PRESENT_CATEGORY(404, "Not Found", "카테고리가 존재하지 않습니다."),
    DUPLICATE_UNIT(409, "Conflict", "이미 존재하는 요소가 있습니다."),
    CONTAIN_EMPTY_REQUEST(400, "Bad Request", "요청 내용에 Null 또는 공백이 들어갔습니다."),
    LOGIN_UNSUCCESSFUL(401, "Unauthorized", "로그인에 실패했습니다."),
    PERMISSION_ERROR(403, "Forbidden", "권한이 없습니다."),
    SESSION_EXPIRED(401, "Unauthorized", "세션이 만료되거나 존재하지 않습니다."),
    SUSPENSION_ERROR(403, "Forbidden", "현재 계정은 활동 정지 된 상태입니다."),
    SCORE_OUT_OF_RANGE(400, "Bad Request", "리뷰 점수는 0 ~ 10점 사이만 허용됩니다.");

    private final Integer statusCode;
    private final String statusName;
    private final String statusMessage;

    private StatusEnum(Integer statusCode, String statusName, String statusMessage) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.statusMessage = statusMessage;
    }
}
