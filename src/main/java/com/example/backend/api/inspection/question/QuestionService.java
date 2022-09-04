package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.api.inspection.question.model.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionRepositorySupport questionRepositorySupport;

    public List<QuestionDto.Summary> getQuestionsByInspectionIdx(int inspectionIdx, Pageable pageable){
        List<Question> questionList = questionRepositorySupport.findByInspectionIdx(inspectionIdx, pageable);
        return questionList.stream().map(v -> new QuestionDto.Summary(v)).collect(Collectors.toList());
    }
    public List<QuestionDto.Detail> getQuestionListWithAnswers(int inspectionIdx, Pageable pageable){
        List<Question> questionList = questionRepositorySupport.findByInspectionIdx(inspectionIdx, pageable);
        return questionList.stream().map(v -> new QuestionDto.Detail(v)).collect(Collectors.toList());
    }
    public QuestionDto.Detail getQuestionDetail(int questionIdx){
        Question questionDetail = questionRepositorySupport.findByQuestionIdx(questionIdx);
        return new QuestionDto.Detail(questionDetail);
    }
    public List<QuestionDto.Detail> getQuestionsByInspectionIdxAndQuestionPage(int inspectionIdx, int questionPage){
        List<Question> questionList = questionRepositorySupport.findByInspectionIdxAndQuestionPage(inspectionIdx, questionPage);
        return questionList.stream().map(v -> new QuestionDto.Detail(v)).collect(Collectors.toList());
    }

    public void deleteQuestion(int questionIdx){
        questionRepositorySupport.deleteQuestion(questionIdx);
    }

    public void updateQuestion(int questionIdx, Question question){
        questionRepositorySupport.updateQuestion(questionIdx, question);
    }

    public void insertQuestions(List<Question> questions){
        questionRepository.saveAll(questions);
    }

}
