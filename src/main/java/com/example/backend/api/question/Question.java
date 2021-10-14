package com.example.backend.api.question;

import com.example.backend.api.answer.Answer;
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
    private String delYn;
    private QuestionType questionType;

    private List<Answer> answers;

}
