package com.example.backend.api.result;

import com.example.backend.api.question.Question;
import lombok.Data;

import java.util.List;

@Data
public class Result {

    private int resultIdx;
    private String resultName;
    private String resultTitle;
    private String mainSentence;
    private String subSentence;
    private String goodKeyword;
    private String badKeyword;
    private int goodResultIdx;
    private int badResultIdx;
    private int inspectionIdx;
    private int questionPage;

    private Result goodResult;
    private Result badResult;

    private List<Question> questionList;

}
