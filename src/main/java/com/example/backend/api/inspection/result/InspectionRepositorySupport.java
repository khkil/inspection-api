package com.example.backend.api.inspection.result;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.QInspection;
import com.example.backend.api.inspection.question.model.QQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InspectionRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public InspectionRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Inspection.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    QInspection inspection = QInspection.inspection;
    QQuestion question = QQuestion.question;

    public List<Inspection> findAll(){
        List<Inspection> inspectionList = jpaQueryFactory
                .selectFrom(inspection)
                .fetch();

        return inspectionList;
    }

    public Inspection findByInspectionIdx(int inspectionIdx){
        Inspection inspectionDetail = jpaQueryFactory
                .select(inspection)
                .from(inspection)
                .where(inspection.inspectionIdx.eq(inspectionIdx))
                .fetchOne();

        return inspectionDetail;
    }
}
