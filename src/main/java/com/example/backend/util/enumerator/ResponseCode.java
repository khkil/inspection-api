package com.example.backend.util.enumerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(0, "success"),
    FAIL(-1, "fail"),

    USER_UNAUTHORIZED(-1001, "user_unauthorized"),
    KAKAO_USER_NOT_SIGNED(-2001, "kakao_user_not_signed"),
    NAVER_USER_NOT_SIGNED(-3001, "naver_user_not_signed");

    int code;
    String msg;

}