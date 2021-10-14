package com.example.backend.api.question;

import com.example.backend.api.answer.Answer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {

    List<Question> getQuestionList(int idx);
    List<String> getPageInfo(@Param("inspection_idx") int inspection_idx, @Param("page") int page);
    Question getQuestionDetail(int questionIdx);
    void deleteQuestion(int questionIdx);
    void updateQuestion(@Param("questionIdx") int questionIdx, @Param("question") Question question);
    void updateAnswers(List<Answer> answers);

}
