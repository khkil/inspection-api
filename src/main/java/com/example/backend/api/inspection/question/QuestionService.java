package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.api.inspection.result.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionRepositorySupport questionRepositorySupport;

    public List<Question> getQuestionsByInspectionIdx(int inspectionIdx){
        return questionRepositorySupport.findByInspectionIdx(inspectionIdx);
    }
    public List<?> getQuestionInfo(int inspectionIdx){
        return questionMapper.getQuestionList(inspectionIdx);
    }
    public List<Result> getQuestionsOfInspection(int inspectionIdx){
        return questionMapper.getQuestionsOfInspection(inspectionIdx);
    }

    public  Question getQuestionDetail(int questionIdx){
        return questionMapper.getQuestionDetail(questionIdx);
    }
    public List<String> getPageInfo(int inspection_idx, int page){
        return questionMapper.getPageInfo(inspection_idx, page);
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
