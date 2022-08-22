package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.answer.model.QAnswer;
import com.example.backend.api.inspection.question.model.QQuestion;
import com.example.backend.api.inspection.question.model.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Question.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QQuestion question = QQuestion.question;
    QAnswer answer = QAnswer.answer;

    public List<Question> findByInspectionIdx(int inspectionIdx){
        List<Question> results = new ArrayList<>();
        List<Question> questionList = jpaQueryFactory
                .selectFrom(question)
                //.leftJoin(question.answers, answer)
                .where(question.inspectionIdx.eq(inspectionIdx))
                .fetch();
        for(Question question : questionList){
            Question q = new Question();
            q.setInspectionIdx(question.getInspectionIdx());
            results.add(q);
        }

        return results;
    }
}
