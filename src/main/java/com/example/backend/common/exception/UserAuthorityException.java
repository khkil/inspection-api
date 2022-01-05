package com.example.backend.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthorityException extends ApiException{
    public UserAuthorityException(String msg) {
        super(msg);
    }
}
