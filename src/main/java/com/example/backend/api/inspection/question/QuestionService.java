package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.api.inspection.question.model.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionRepositorySupport questionRepositorySupport;

    public List<QuestionDto.Summary> getQuestionsByInspectionIdx(int inspectionIdx){
        List<Question> questionList = questionRepositorySupport.findByInspectionIdx(inspectionIdx);
        return questionList.stream().map(v -> new QuestionDto.Summary(v)).collect(Collectors.toList());
    }
    public List<QuestionDto.Detail> getQuestionListWithAnswers(int inspectionIdx){
        List<Question> questionList = questionRepositorySupport.findByInspectionIdx(inspectionIdx);
        return questionList.stream().map(v -> new QuestionDto.Detail(v)).collect(Collectors.toList());
    }
    public  Question getQuestionDetail(int questionIdx){
        return questionMapper.getQuestionDetail(questionIdx);
    }
    public List<QuestionDto.Detail> getQuestionsByInspectionIdxAndQuestionPage(int inspectionIdx, int questionPage){
        List<Question> questionList = questionRepositorySupport.findByInspectionIdxAndQuestionPage(inspectionIdx, questionPage);
        return questionList.stream().map(v -> new QuestionDto.Detail(v)).collect(Collectors.toList());
    }

    public void deleteQuestion(int questionIdx){
        questionMapper.deleteQuestion(questionIdx);
    }

    public void updateQuestion(int questionIdx, Question question){
        questionMapper.updateQuestion(questionIdx, question);
    }

    public void insertQuestions(List<Question> questions){
        questionMapper.insertQuestions(questions);
    }

}
