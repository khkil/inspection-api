package com.example.backend.api.inspection.question.answer.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
public class MemberAnswerDto {

    private int memberIdx;
    private int questionIdx;
    private int answerIdx;

    public MemberAnswer toEntity(){
        return MemberAnswer.builder()
                .memberAnswerID(new MemberAnswerID(memberIdx, questionIdx))
                .answerIdx(answerIdx)
                .build();
    }

}
