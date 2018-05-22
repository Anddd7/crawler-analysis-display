package com.github.anddd7.boot.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(BAD_REQUEST, message);
    }
}
