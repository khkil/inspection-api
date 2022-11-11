package com.example.backend.api.inspection.question;

import com.example.backend.api.inspection.question.model.QQuestion;
import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.config.database.EntityMapper;
import com.example.backend.util.QueryDslUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLInsertClause;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class QuestionRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final SQLQueryFactory sqlQueryFactory;

    public QuestionRepositorySupport(JPAQueryFactory jpaQueryFactory, SQLQueryFactory sqlQueryFactory) {
        super(Question.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.sqlQueryFactory = sqlQueryFactory;
    }

    QQuestion question = QQuestion.question;

    public List<Question> findByInspectionIdx(int inspectionIdx, Pageable pageable){
        List<Question> questionList = jpaQueryFactory
                .selectFrom(question)
                .where(question.inspectionIdx.eq(inspectionIdx))
                //.offset(pageable.getOffset())
                //.limit(pageable.getPageSize())
                .orderBy(orderBy(pageable))
                .fetch();
        return questionList;
    }

    public List<Question> findByInspectionIdxAndQuestionPage(int inspectionIdx, int questionPage){
        List<Question> questionList = jpaQueryFactory
                .selectFrom(question)
                .where(
                        inspectionIdxEq(inspectionIdx),
                        questionPageEq(questionPage),
                        delYnEq("N")
                )
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
                .set(question.questionText, params.getQuestionText())
                .set(question.delYn, params.getDelYn())
                .where(question.questionIdx.eq(questionIdx))
                .execute();
    }

    public void deleteQuestion(int questionIdx){
        jpaQueryFactory
                .update(question)
                .set(question.delYn, "Y")
                .where(question.questionIdx.eq(questionIdx))
                .execute();
    }

    private BooleanExpression inspectionIdxEq(int inspectionIdx){
        return inspectionIdx > 0 ? question.inspectionIdx.eq(inspectionIdx) : null;
    }


    private BooleanExpression questionPageEq(int questionPage){
        return questionPage > 0 ? question.questionPage.eq(questionPage) : null;
    }

    private BooleanExpression delYnEq(String delYn){
        return delYn != null ? question.delYn.eq(delYn) : null;
    }

    private OrderSpecifier<?> orderBy(Pageable pageable){

        if(!pageable.getSort().isEmpty()){
            for(Sort.Order order : pageable.getSort()){
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()){
                    case "questionNumber": return new OrderSpecifier(direction, question.questionNumber);
                    case "questionText": return new OrderSpecifier(direction, question.questionText);
                }
            }
        }
        return new OrderSpecifier(Order.DESC, question.questionIdx);
    }
}
