package com.example.backend.api.inspection.result.model;

import com.example.backend.api.inspection.question.model.Question;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultIdx;
    @NotNull
    private String resultName;
    @NotNull
    private String resultTitle;
    private String mainSentence;
    private String subSentence;
    private String goodKeyword;
    private String badKeyword;
    private int inspectionIdx;

    @OneToMany
    @JoinColumn(name = "result_idx")
    private List<Question> questionList;

}
