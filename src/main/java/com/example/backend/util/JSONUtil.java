package com.example.backend.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JSONUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);
    private static final String DESC = "desc";
    private static final String asc = "asc";

    public static JSONArray SortJsonArray(JSONArray arr, String key){

        List<JSONObject> jsonValues = new ArrayList<>();
        JSONArray sortedJsonArray = new JSONArray();

        for(int i = 0; i < arr.length(); i ++){
            jsonValues.add(arr.getJSONObject(i));
        }

        Collections.sort(jsonValues, (o1, o2) -> {

            String valA = new String();
            String valB = new String();

            try {
                valA = o1.optString(key);
                valB = o2.optString(key);
            }catch (JSONException e){
                logger.error("key is null");

            }
            return valB.compareTo(valA);
        });


        for (int i = 0; i < arr.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }

        return sortedJsonArray;
    }
}
