package com.cvgenerator.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
        log.error(message);
    }
}
