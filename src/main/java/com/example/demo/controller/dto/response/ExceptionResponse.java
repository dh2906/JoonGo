package com.example.demo.controller.dto.response;

import com.example.demo.exception.ExceptionGenerator;
import lombok.Getter;

@Getter
public class ExceptionResponse {
    Integer ExceptionStatusCode;
    String ExceptionStatusName;
    String ExceptionStatusMessage;

    public ExceptionResponse(ExceptionGenerator e) {
        this.ExceptionStatusCode = e.getStatusCode();
        this.ExceptionStatusName = e.getStatusName();
        this.ExceptionStatusMessage = e.getStatusMessage();
    }
}
