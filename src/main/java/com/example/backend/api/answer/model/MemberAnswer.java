package com.example.backend.api.answer.model;

import lombok.Data;

import java.util.Map;

@Data
public class MemberAnswer {

    private int memberIdx;
    private int questionIdx;
    private int answerIdx;

    private Map<String, Integer> answerMap;
}
