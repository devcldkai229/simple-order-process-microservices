package com.khaircloud.notification.common.exception;

public class ResourceNotExistException extends BaseException {

    public final int code = 404;

    public ResourceNotExistException(String message) {
        super(message);
    }
}
