package com.xm.community.community.exception;

/**
 * @author XM
 * @version 1.0
 * @data 2020-07-01
 * @description CustomizeException
 */
public class CustomizeException extends RuntimeException {
    private String message;
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
