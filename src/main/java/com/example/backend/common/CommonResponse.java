package com.example.backend.common;

import com.example.backend.util.enumerator.ResponseCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CommonResponse {

    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static CommonResponse successResult() {
        CommonResponse result = new CommonResponse();
        result.setSuccess(true);
        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMsg(ResponseCode.SUCCESS.getMsg());

        return result;
    }
    public static CommonResponse successResult(Object data) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMsg(ResponseCode.SUCCESS.getMsg());


        return result;
    }

    public static CommonResponse failResult(String msg) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(ResponseCode.FAIL.getCode());
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
