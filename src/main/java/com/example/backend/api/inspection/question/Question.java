package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.answer.model.Answer;
import com.example.backend.util.enumerator.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class Question {

    private int questionIdx;
    private int inspectionIdx;
    private int resultIdx;
    private int questionPage;
    private int questionNumber;
    private String questionText;
    private int questionOrder;
    private String delYn;
    private QuestionType questionType;
    private QuestionType answerType;
    private String filePath;

    private List<Answer> answers;

}
