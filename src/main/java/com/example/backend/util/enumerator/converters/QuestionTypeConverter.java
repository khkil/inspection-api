package com.example.backend.util.enumerator.converters;

import com.example.backend.util.enumerator.QuestionType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@Converter
public class QuestionTypeConverter implements AttributeConverter<QuestionType, String> {

    @Override
    public String convertToDatabaseColumn(QuestionType attribute) {
        return attribute.getQuestionType();
    }

    @Override
    public QuestionType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return QuestionType.getText(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
