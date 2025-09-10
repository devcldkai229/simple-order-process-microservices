package com.khaircloud.identity.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class EmailAlreadyExistException extends BaseException {

    private final int code = 400;

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
