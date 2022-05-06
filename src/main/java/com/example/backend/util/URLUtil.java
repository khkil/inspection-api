package com.example.backend.util;

import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.commons.compress.utils.Lists;

import org.apache.http.client.utils.URLEncodedUtils;

public class URLUtil {

    public static String paramToQueryString(Map<String, Object> paramMap){
        List<NameValuePair> params = Lists.newArrayList();
        if(paramMap != null){
            for(Map.Entry<String, Object> paramEntry : paramMap.entrySet()){
                Object value = paramEntry.getValue();
                if(value != null){
                    params.add(new BasicNameValuePair(paramEntry.getKey(), value.toString()));
                }
            }
        }
        return URLEncodedUtils.format(params, "UTF-8");
    }
}
