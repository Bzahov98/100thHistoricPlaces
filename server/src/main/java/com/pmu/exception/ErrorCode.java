package com.pmu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode implements ErrorCodeInterface {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_USED(HttpStatus.BAD_REQUEST),
    INCORRECT_FORMAT_OF_PASSWORD(HttpStatus.BAD_REQUEST,"Please eneter password longer than 8 symbols."),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_ENOUGH_CLOSE_TO_PLACE(HttpStatus.NOT_FOUND),
    CANNOT_MAP_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR);
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
