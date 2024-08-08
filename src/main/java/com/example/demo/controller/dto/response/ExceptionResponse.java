package com.example.demo.controller.dto.response;

import com.example.demo.exception.ExceptionGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    Integer exceptionStatusCode;
    String exceptionStatusName;
    String exceptionStatusMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime suspensionEndTime;

    public ExceptionResponse(ExceptionGenerator e) {
        this.exceptionStatusCode = e.getStatusCode();
        this.exceptionStatusName = e.getStatusName();
        this.exceptionStatusMessage = e.getStatusMessage();
        this.suspensionEndTime = e.getSuspensionEndTime();
    }
}
