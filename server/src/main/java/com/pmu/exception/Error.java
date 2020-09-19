package com.pmu.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private ErrorCodeInterface errorCode;
    private String message;
    private Map<String, String> messages;
}