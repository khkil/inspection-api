package com.example.backend.common;

import lombok.Data;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class CommonResponse {

    private boolean success;
    private int code;
    private String msg;
    private Object data;

    private enum CommonResult {
        SUCCESS(0, "success"),
        FAIL(-1, "fail");

        int code;
        String msg;

        CommonResult(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private int getCode() {
            return code;
        }
        private String getMsg() {
            return msg;
        }
    }

    public static CommonResponse successResult() {
        CommonResponse result = new CommonResponse();
        result.setSuccess(true);
        result.setCode(CommonResult.SUCCESS.getCode());
        result.setMsg(CommonResult.SUCCESS.getMsg());

        return result;
    }
    public static CommonResponse successResult(Object data) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(CommonResult.SUCCESS.getCode());
        result.setMsg(CommonResult.SUCCESS.getMsg());


        return result;
    }

    public static CommonResponse failResult(String msg) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(CommonResult.FAIL.getCode());
        result.setMsg(msg);
        return result;
    }

    public static Map<String, Object> getDataMap(String key, Object data){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(key, data);
        return dataMap;
    }
}
