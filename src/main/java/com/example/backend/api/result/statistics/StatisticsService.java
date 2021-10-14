package com.example.backend.api.result.statistics;

import com.example.backend.api.question.Question;
import com.example.backend.api.result.Result;
import com.example.backend.util.GroundUtil;
import com.example.backend.util.JSONUtil;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class StatisticsService {

    @Autowired StatisticsMapper statisticsMapper;

    public JSONObject getGroundStatistics(int inspectionIdx){
        JSONObject statisticsResult = new JSONObject();

        for(Statistics statistics: statisticsMapper.getGroundStatistics(inspectionIdx)){
            /*int groupIdx = statistics.getGroupIdx();
            boolean hasGroup = groupIdx > 0;
            if(!hasGroup) continue;*/

            String key = "user_" + statistics.getUserIdx();
            String resultName = GroundUtil.resultMap.get(statistics.getResultIdx());
            statistics.setResultName(resultName);
            JSONObject result = new JSONObject();
            result.put("name", statistics.getResultName());
            result.put("score", statistics.getResultScore());

            if(!statisticsResult.has(key)){
                JSONObject obj = new JSONObject();
                JSONArray results = new JSONArray();
                results.put(result);

                obj.put("userName", statistics.getUserName());
                obj.put("groupName", statistics.getGroupName());
                obj.put("userGrade", statistics.getUserGrade());
                obj.put("userEtc", statistics.getUserEtc());
                obj.put("results", results);
                statisticsResult.put(key, obj);


            }else{
                JSONObject obj = statisticsResult.getJSONObject(key);
                obj.getJSONArray("results").put(result);

            }

        }

        Iterator<String> iter = statisticsResult.keys();
        int lowestGrade = 3;
        while(iter.hasNext()){
            String key = iter.next();
            JSONObject value = statisticsResult.getJSONObject(key);
            JSONArray results = value.getJSONArray("results");

            JSONArray grades = JSONUtil.SortJsonArray(results, "score");
            //1,2,3위 순위 구하기

            /*for(int i=0; i<results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                if(grades.length() < lowestGrade){
                    grades.put(result);
                }else{

                }


                int resultScore = result.getInt("score");

                System.out.println(grades);
            }*/



            /*System.out.println("results");
            System.out.println(results);

            System.out.println("grade");
            System.out.println(grades);
            System.out.println();*/

            System.out.println("grades" + grades);
            System.out.println("results" +  results);
            value.put("grades", grades);
        }
        return statisticsResult;
    }
}
