package com.example.backend.util.enumerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
    TEXT("TEXT"),
    IMAGE("IMAGE");

    private String questionType;

}