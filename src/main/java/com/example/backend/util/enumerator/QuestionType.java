package com.example.backend.util.enumerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
    TEXT("TEXT"),
    IMAGE("IMAGE");

    private String questionType;

    public static QuestionType getText(String dbData) {
        for (QuestionType value : QuestionType.values()) {
            if (value.getQuestionType().equals(dbData)) {
                return value;
            }
        }
        return null;
    }

}