package com.example.backend.api.inspection.question.model;

import com.example.backend.api.inspection.question.answer.model.Answer;
import com.example.backend.util.enumerator.QuestionType;
import com.example.backend.util.enumerator.converters.QuestionTypeConverter;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_idx", nullable = false)
    private int questionIdx;

    @Column(name = "inspection_idx")
    private int inspectionIdx;

    @Column(name = "result_idx")
    private int resultIdx;

    @Column(name = "question_page")
    private int questionPage;

    @Column(name = "question_number")
    private int questionNumber;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "question_order")
    private int questionOrder;

    @Column(name = "del_yn")
    private String delYn;

    @Column(name = "question_type")
    @Convert(converter = QuestionTypeConverter.class)
    private QuestionType questionType;

    @Column(name = "answer_type")
    @Convert(converter = QuestionTypeConverter.class)
    private QuestionType answerType;

    //private List<Object> filePath;
    @OneToMany
    @JoinColumn(name = "question_idx")
    private List<Answer> answers;



}
