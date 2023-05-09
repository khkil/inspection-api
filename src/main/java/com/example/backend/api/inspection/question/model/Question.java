package com.example.backend.api.inspection.question.model;

import com.example.backend.api.inspection.question.answer.model.Answer;
import com.example.backend.util.enumerator.QuestionType;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_idx", nullable = false)
    private Integer questionIdx;

    @Column(name = "inspection_idx")
    private Integer inspectionIdx;

    @Column(name = "result_idx")
    private Integer resultIdx;

    @Column(name = "question_page")
    private Integer questionPage;

    @NotNull
    @Column(name = "question_number")
    private Integer questionNumber;

    @NotNull
    @Column(name = "question_text")
    private String questionText;

    @Column(name = "question_order")
    private Integer questionOrder;

    @Column(name = "del_yn")
    private String delYn;

    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(name = "answer_type")
    @Enumerated(EnumType.STRING)
    private QuestionType answerType;

    //private List<Object> filePath;
    @OneToMany
    @JoinColumn(name = "question_idx")
    @OrderBy("answerIdx desc")
    private List<Answer> answers;

}
