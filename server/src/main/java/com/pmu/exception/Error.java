package com.pmu.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Error {

    private ErrorCodeInterface errorCode;
    private String message;
}