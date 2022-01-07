package com.example.backend.api.question;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface QuestionMapper {

    List<Question> getQuestionList(int idx);
    List<String> getPageInfo(@Param("inspection_idx") int inspection_idx, @Param("page") int page);
    Question getQuestionDetail(int questionIdx);
    void deleteQuestion(int questionIdx);
    void updateQuestion(@Param("questionIdx") int questionIdx, @Param("question") Question question);
    void updateQuestions(List<Question> questions);

}
