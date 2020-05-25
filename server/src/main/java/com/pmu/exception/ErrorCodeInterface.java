package com.pmu.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {
    HttpStatus getHttpStatus();

}
