package com.example.backend.api.util.coolsms;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CoolsmsService {

    private final String COOLSMS_API_KEY =  "NCSWR6TUBVHJZ0RR";
    private final String COOLSMS_API_SECRET =  "FJTG6U7BXKJQTJQS6PTIJLLIIXRASGSM";
    private final String COOLSMS_FROM_PHONE =  "010-2031-8057";

    public void sendSms(Coolsms coolSms){
        ObjectMapper mapObject = new ObjectMapper();
        coolSms.setType("SMS");
        coolSms.setFrom(COOLSMS_FROM_PHONE);
        HashMap<String, String> params = mapObject.convertValue(coolSms, HashMap.class);
        Message coolsms = new Message(COOLSMS_API_KEY, COOLSMS_API_SECRET);

        try {
            coolsms.send(params);
        }catch (CoolsmsException e){
            e.printStackTrace();
        }
    }
}
