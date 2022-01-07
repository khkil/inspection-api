package com.example.backend.api.answer;

import com.example.backend.api.answer.model.MemberAnswer;
import com.example.backend.api.inspection.Inspection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AnswerMapper {

    void insertMemberAnswer(@Param("memberIdx") int memberIdx, @Param("answerMap") Map<String, Integer> answerMap);
}
