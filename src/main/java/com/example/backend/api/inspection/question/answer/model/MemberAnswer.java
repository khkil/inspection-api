package com.example.backend.api.inspection.question.answer.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_answer")
public class MemberAnswer {

    @EmbeddedId
    private MemberAnswerID memberAnswerID;

    @Column(name = "answer_idx")
    private int answerIdx;


}
