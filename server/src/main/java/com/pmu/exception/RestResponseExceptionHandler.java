package com.pmu.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Error> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(new Error(ex.getErrorCode(), ex.getMessage()), ex.getErrorCode().getHttpStatus());
    }
}
