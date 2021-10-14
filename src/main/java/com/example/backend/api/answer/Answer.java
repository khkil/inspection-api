package com.example.backend.api.answer;

import com.example.backend.api.util.gcs.model.File;
import lombok.Data;

import java.util.List;

@Data
public class Answer {

    private int answerIdx;
    private int questionIdx;
    private String answerText;
    private int answerScore;
    private String filePath;

}
