package com.example.backend.api.inspection.question.answer;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AnswerMapper {

    void insertMemberAnswer(@Param("memberIdx") int memberIdx, @Param("answerMap") Map<String, Integer> answerMap);
}
