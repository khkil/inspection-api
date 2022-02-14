package com.example.backend.api.user;

import com.example.backend.api.inspection.question.model.Question;
import lombok.Data;

@Data
public class UserAnswer {

    int userIdx;
    int questionIdx;
    int answerIdx;

    private Question question;

}
