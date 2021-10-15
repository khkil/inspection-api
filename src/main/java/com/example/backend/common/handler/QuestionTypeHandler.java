package com.example.backend.common.handler;

import com.example.backend.util.enumerator.QuestionType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionTypeHandler<E extends Enum<E>> implements TypeHandler<QuestionType> {
    private Class<E> type;

    public QuestionTypeHandler(Class<E> type) {
        this.type = type;
    }

    public void setParameter(PreparedStatement preparedStatement, int i, QuestionType questionType, JdbcType jdbcType) throws
            SQLException {
        preparedStatement.setString(i, questionType.getQuestionType());
    }

    @Override
    public QuestionType getResult(ResultSet resultSet, String s) throws SQLException {
        String questionType = resultSet.getString(s);
        return getQuestionTypes(questionType);
    }

    @Override
    public QuestionType getResult(ResultSet resultSet, int i) throws SQLException {
        String questionType = resultSet.getString(i);
        return getQuestionTypes(questionType);
    }

    @Override
    public QuestionType getResult(CallableStatement callableStatement, int i) throws SQLException {
        String questionType = callableStatement.getString(i);
        return getQuestionTypes(questionType);
    }

    private QuestionType getQuestionTypes(String text) {
        try {
            QuestionType[] enumConstants = (QuestionType[])type.getEnumConstants();
            for (QuestionType questionType : enumConstants) {
                if (questionType.getQuestionType().equals(text)) {
                    return questionType;
                }
            }
            return null;
        } catch (Exception exception) {
            throw new TypeException("Can't make enum object '" + type + "'", exception);
        }
    }

}

