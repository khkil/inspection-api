package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.model.QQuestion;
import com.example.backend.api.inspection.question.model.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionRepository(JPAQueryFactory jpaQueryFactory) {
        super(Question.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QQuestion question = QQuestion.question;

    public List<Question> findByInspectionIdx(int inspectionIdx){
        List<Question> questionList = jpaQueryFactory
                .selectFrom(question)
                .where(question.inspectionIdx.eq(inspectionIdx))
                .fetch();
        return questionList;
    }

    public List<Question> findByInspectionIdxAndQuestionPage(int inspectionIdx, int questionPage){
        List<Question> questionList = jpaQueryFactory
                .selectFrom(question)
                .where(question.inspectionIdx.eq(inspectionIdx)
                .and(question.questionPage.eq(questionPage)))
                .fetch();
        return questionList;
    }

    public Question findByQuestionIdx(int questionIdx){
        Question questionDetail = jpaQueryFactory
                .selectFrom(question)
                .where(question.questionIdx.eq(questionIdx))
                .fetchOne();
        return questionDetail;
    }

    public void updateQuestion(int questionIdx, Question params){
        jpaQueryFactory
                .update(question)
                .set(question.questionText, params.getQuestionText())
                .set(question.questionType, params.getQuestionType())
                .set(question.answerType, params.getAnswerType())
                //.set(question.f, params.getQuestionText())
                .set(question.delYn, params.getDelYn())
                .where(question.questionIdx.eq(questionIdx))
                .execute();
    }
}
