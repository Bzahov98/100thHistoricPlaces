package com.pmu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode implements ErrorCodeInterface {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND);
    @Getter
    private HttpStatus httpStatus;

    @Getter
    private String message;

    ErrorCode(org.springframework.http.HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    ErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
