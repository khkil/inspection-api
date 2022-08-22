package com.example.backend.api.inspection.question.answer.model;

import com.example.backend.api.util.gcs.model.File;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Getter
@Entity
public class Answer {
    @Id
    @Column(name = "answer_idx", nullable = false)
    private int answerIdx;

    @Column(name = "question_idx")
    private int questionIdx;
    private String answerText;
    private int answerScore;
    //private List<Object> filePath;
    private String delYn;



}
