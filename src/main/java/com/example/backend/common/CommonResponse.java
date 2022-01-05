package com.example.backend.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CommonResponse {

    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public enum CodeEnum {
        SUCCESS(0, "success"),
        FAIL(-1, "fail"),

        INTERNAL_SERVER_ERROR(500, "interval_server_error"),
        BAD_REQUEST(400, "bad_request"),
        FORBIDDEN(403, "forbidden"),

        USER_UNAUTHORIZED(-1001, "user_unauthorized"),
        ;



        int code;
        String msg;

        CodeEnum(int code, String msg) {
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

    public static CommonResponse successResult() {
        CommonResponse result = new CommonResponse();
        result.setSuccess(true);
        result.setCode(CodeEnum.SUCCESS.getCode());
        result.setMsg(CodeEnum.SUCCESS.getMsg());

        return result;
    }
    public static CommonResponse successResult(Object data) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(CodeEnum.SUCCESS.getCode());
        result.setMsg(CodeEnum.SUCCESS.getMsg());


        return result;
    }

    public static CommonResponse failResult(String msg) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(CodeEnum.FAIL.getCode());
        result.setMsg(msg);
        return result;
    }

    public static CommonResponse failResult(int code, String msg) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Map<String, Object> getDataMap(String key, Object data){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(key, data);
        return dataMap;
    }
}
