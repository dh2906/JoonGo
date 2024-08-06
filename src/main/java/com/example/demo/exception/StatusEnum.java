package com.example.demo.exception;

public enum StatusEnum {
    READ_NOT_PRESENT_MEMBER(404, "Not Found", "멤버가 존재하지 않습니다."),
    READ_NOT_PRESENT_AUTHOR(404, "Not Found", "작성자가 존재하지 않습니다."),
    READ_NOT_PRESENT_SELLER(404, "Not Found", "판매자가 존재하지 않습니다."),
    READ_NOT_PRESENT_PRODUCT(404, "Not Found", "상품이 존재하지 않습니다."),
    READ_NOT_PRESENT_REVIEW(404, "Not Found", "리뷰가 존재하지 않습니다."),
    READ_NOT_PRESENT_CATEGORY(404, "Not Found", "카테고리가 존재하지 않습니다."),
    CREATE_OR_EDIT_DUPLICATE_UNIT(409, "Conflict", "이미 존재하는 요소가 있습니다."),
    CREATE_OR_EDIT_EMPTY_REQUEST(400, "Bad Request", "요청 내용에 Null 또는 공백이 들어갔습니다."),
    LOGIN_UNSUCCESSFUL(401, "Unauthorized", "로그인에 실패했습니다."),
    PERMISSION_ERROR(401, "Unauthorized", "권한이 없습니다."),
    SESSION_EXPIRED(401, "Unauthorized", "세션이 만료되거나 존재하지 않습니다."),
    SCORE_OUT_OF_RANGE(400, "Bad Request", "리뷰 점수는 0 ~ 10점 사이만 허용됩니다.");

    private final Integer statusCode;
    private final String statusName;
    private final String statusMessage;

    private StatusEnum(Integer statusCode, String statusName, String statusMessage) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
