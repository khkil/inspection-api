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

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "answer_score")
    private int answerScore;
    //private List<Object> filePath;

    @Column(name = "del_yn")
    private String delYn;



}
