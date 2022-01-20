package com.example.backend.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class HttpUrlConnectionUtil {



    public String requestToServer(String requestUrl, HttpMethod method, Map<String, String> params, HttpHeaders headers) throws IOException{

        URL url = new URL(requestUrl);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(method.name());
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        if(params != null){
            StringBuilder postData = new StringBuilder();
            for(Map.Entry<String,String> param : params.entrySet()) {
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.getOutputStream().write(postDataBytes);
        }

        if(headers != null){
            headers.forEach((key, values) -> {
                for (String value : values) {
                    conn.setRequestProperty(key, value);
                }
            });
        }



        /*int responseCode = conn.getResponseCode();

        if(responseCode != 200){
            throw new IOException("request error");
        }*/

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();
        return response.toString();
    }
}
