package com.example.demo.global.except;

import java.time.LocalDateTime;

public class ExceptionGenerator extends RuntimeException{
    private final StatusEnum statusEnum;
    private LocalDateTime suspensionEndTime;

    public ExceptionGenerator(StatusEnum statusEnum) {
        this(statusEnum, null);
    }

    public ExceptionGenerator(StatusEnum statusEnum, LocalDateTime suspensionEndTime) {
        this.statusEnum = statusEnum;
        this.suspensionEndTime = suspensionEndTime;
    }

    public Integer getStatusCode() {
        return statusEnum.getStatusCode();
    }

    public String getStatusName() {
        return statusEnum.getStatusName();
    }

    public String getStatusMessage() {
        return statusEnum.getStatusMessage();
    }

    public LocalDateTime getSuspensionEndTime() {
        return suspensionEndTime;
    }
}
