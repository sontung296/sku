package com.base.service.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApplicationBaseException extends RuntimeException{
    private final Integer code;
    private final String detailMessage;
    private final String message;

}
