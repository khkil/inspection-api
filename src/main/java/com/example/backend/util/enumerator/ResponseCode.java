package com.example.backend.util.enumerator;



public enum ResponseCode {
    SUCCESS(0, "success"),
    FAIL(-1, "fail"),

    USER_UNAUTHORIZED(-1001, "user_unauthorized"),
    KAKAO_USER_NOT_SIGNED(-2001, "kakao_user_not_signed"),
    ;



    int code;
    String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}