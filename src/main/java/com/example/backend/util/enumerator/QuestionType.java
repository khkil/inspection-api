package com.example.backend.util.enumerator;

import com.example.backend.common.handler.QuestionTypeHandler;
import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;

@Getter
public enum QuestionType {
    TEXT(0),
    IMAGE(1);

    private int questionType;

    QuestionType(int questionType) {
        this.questionType = questionType;
    }

    @MappedTypes(QuestionType.class)
    public static class TypeHandler extends QuestionTypeHandler<QuestionType> {
        public TypeHandler() {
            super(QuestionType.class);
        }
    }
}
