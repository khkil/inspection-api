package com.example.backend.api.inspection.question.answer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MemberAnswerID implements Serializable {

    @Column(name = "member_idx")
    private int memberIdx;

    @Column(name = "question_idx")
    private int questionIdx;

}
