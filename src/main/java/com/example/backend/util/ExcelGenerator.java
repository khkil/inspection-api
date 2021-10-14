package com.example.backend.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import com.example.backend.api.question.Question;
import com.example.backend.api.user.User;
import com.example.backend.api.user.UserAnswer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

public class ExcelGenerator {

    private static final String EXCEL_EXTENSION = ".xlsx";

    public static ByteArrayInputStream groundStatisticsExcel(JSONObject data) throws IOException {
        String[] userColumns = {"이름", "기관", "학년", "반"};

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("Customers");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);



        int rowIdx = 0;
        int cellIdx = 0;

        Row headerRow = sheet.createRow(rowIdx++);

        for (int col = 0; col < userColumns.length; col++) {
            Cell cell = headerRow.createCell(cellIdx++);
            cell.setCellValue(userColumns[col]);
            cell.setCellStyle(headerCellStyle);
        }

        headerCellStyle = workbook.createCellStyle();
        //headerCellStyle.setFillPattern(FillPatternType.DIAMONDS);

        for(Integer key : GroundUtil.resultMap.keySet()){

            Cell cell = headerRow.createCell(cellIdx++);
            cell.setCellValue(GroundUtil.resultMap.get(key));
            cell.setCellStyle(headerCellStyle);

        }

        Integer[] gradeColumns = {1, 2 ,3};

        for(int i : gradeColumns){
            Cell cell = headerRow.createCell(cellIdx++);
            cell.setCellValue(i + "순위");
            cell.setCellStyle(headerCellStyle);
        }

        Iterator<String> iter = data.keys();


        while(iter.hasNext()){
            cellIdx = 0;
            String key = iter.next();
            JSONObject value = data.getJSONObject(key);

            Row row = sheet.createRow(rowIdx++);

            Cell cell = row.createCell(cellIdx++);
            cell.setCellValue(value.optString("userName"));

            cell = row.createCell(cellIdx++);
            cell.setCellValue(value.optString("groupName"));

            cell = row.createCell(cellIdx++);
            cell.setCellValue(value.optString("userGrade"));

            cell = row.createCell(cellIdx++);
            cell.setCellValue(value.optString("userEtc"));

            JSONArray results = value.getJSONArray("results");

            for(Object o : results){
                JSONObject result = (JSONObject)o;
                cell = row.createCell(cellIdx++);
                cell.setCellValue(result.getInt("score"));
            }

            JSONArray grades = value.getJSONArray("grades");

            cellIdx = 19;

            for(int i : gradeColumns) {
                int currentIndex = i - 1;
                JSONObject grade = grades.optJSONObject(currentIndex);
                cell = row.createCell(cellIdx++);

                //System.out.println("grades : " + grade);
                if(grade != null){
                    cell.setCellValue(grade.optString("name"));
                }
            }
        }
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

        //http://localhost:8088/api/admin/ground/excel/statistics?inspectionIdx=3
    }



    public static void groundPersonalStatisticsExcel(User user, List<UserAnswer> userAnswers, HttpServletResponse response) throws IOException {
        String[] userColumns = {"이름", "기관명", "학년(나이)", "반", "시행일"};

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("sheet");

        Font boldFont = workbook.createFont();
        boldFont.setBold(true);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        int rowIdx = 0;
        int cellIdx = 0;

        Row row = null;
        Cell cell = null;

        String groupName = user.getGroup() != null ? user.getGroup().getName() : "";
        for (int i = 0; i < userColumns.length; i++) {
            //"이름", "기관명", "학년(나이)", "반", "시행일"
            String cellValue =
                    i == 0 ? user.getUserName()
                            : i == 1 ? groupName
                            : i == 2 ? user.getUserGrade()
                            : i == 3 ? user.getUserEtc()
                            : i == 4 ? user.getCdate()
                            : "";
            cellIdx = 0;
            row = sheet.createRow(rowIdx++);
            cell = row.createCell(cellIdx++);
            cell.setCellValue(userColumns[i]);
            cell.setCellStyle(headerCellStyle);
            cell = row.createCell(cellIdx++);
            cell.setCellValue(cellValue);

        }

        JSONObject answerMap = new JSONObject();
        for(UserAnswer userAnswer : userAnswers){
            Question question = userAnswer.getQuestion();
            int resultIdx = question.getResultIdx();
            int answerIdx = userAnswer.getAnswerIdx();

            String key = "result_" + resultIdx;
            answerMap.accumulate(key, answerIdx);
        }

        List<Integer> countList = new ArrayList<>();
        int maxCount = 0;
        Map<String, Object> rankMap = new HashMap<>();
        for(String key : answerMap.keySet()){
            Map<String, Object> map = new HashMap<>();
            int totalCount = 0;
            JSONArray answers = answerMap.getJSONArray(key);
            if(maxCount < answers.length()){
                maxCount = answers.length();
            }

            for(Object o : answers ){
                int answer = (Integer)o;
                totalCount += answer;
            }

            map.put("totalCount", totalCount);
            if(!countList.contains(totalCount)){
                countList.add(totalCount);
            }
            rankMap.put(key, map);
        }


        for(String key : rankMap.keySet()){
            int rank = 0;
            Map<String, Object> map = (Map<String, Object>) rankMap.get(key);
            int totalCount = (Integer) map.get("totalCount");

            for(int count : countList){
                if(count >= totalCount){
                    rank++;
                }
            }
            map.put("rank", rank);
            rankMap.replace(key, map);
        }

        row = sheet.createRow(rowIdx++);
        cellIdx = 2;
        for(int i = 0; i < maxCount; i++){

            cell = row.createCell(cellIdx++);
            cell.setCellValue((i + 1) + "문항");
        }

        cell = row.createCell(cellIdx++);
        cell.setCellValue("총점");

        cell = row.createCell(cellIdx++);
        cell.setCellValue("순위");


        Font questionFont = boldFont;
        questionFont.setBold(true);
        CellStyle questionCellStyle = workbook.createCellStyle();
        questionCellStyle.setFont(questionFont);

        CellStyle resultCellStyle = workbook.createCellStyle();
        resultCellStyle.setFont(boldFont);


        for(Integer key : GroundUtil.resultMap.keySet()){
            cellIdx = 0;
            String resultKey =  "result_" + key;
            int number = rowIdx - userColumns.length;

            row = sheet.createRow(rowIdx++);
            cell = row.createCell(cellIdx++);
            cell.setCellValue(number + "문항");

            cell = row.createCell(cellIdx++);
            cell.setCellStyle(questionCellStyle);
            cell.setCellValue(GroundUtil.resultMap.get(key));

            JSONArray answers = answerMap.optJSONArray(resultKey);
            if(answers == null) continue;

            for(Object o : answers){
                int answer = (Integer)o;
                cell = row.createCell(cellIdx++);
                cell.setCellValue(answer);
            }

            Map<String, Object> map = (Map<String, Object>) rankMap.get(resultKey);

            cell = row.createCell(cellIdx++);
            cell.setCellStyle(resultCellStyle);
            cell.setCellValue(map.get("totalCount").toString());

            cell = row.createCell(cellIdx++);
            cell.setCellStyle(resultCellStyle);
            cell.setCellValue(map.get("rank").toString());
        }

        String fileName = URLEncoder.encode(groupName + "_" +user.getUserName() + EXCEL_EXTENSION, "UTF-8").replaceAll("\\+", "%20");
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");
        response.setHeader("file_name", fileName);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
