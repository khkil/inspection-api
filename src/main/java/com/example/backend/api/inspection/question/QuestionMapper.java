package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.api.inspection.result.model.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface QuestionMapper {

    void insertQuestions(List<Question> questions);

}
