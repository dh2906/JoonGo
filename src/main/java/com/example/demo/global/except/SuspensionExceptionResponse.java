package com.example.demo.global.except;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class SuspensionExceptionResponse extends ExceptionResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime suspensionEndTime;

    public SuspensionExceptionResponse(ExceptionGenerator e) {
        super(e);
        suspensionEndTime = e.getSuspensionEndTime();
    }
}
