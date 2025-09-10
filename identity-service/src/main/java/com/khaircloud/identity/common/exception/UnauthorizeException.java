package com.khaircloud.identity.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizeException extends BaseException{

    public static final int code = 401;

    public UnauthorizeException(String msg) {
        super(msg);
    }
}
