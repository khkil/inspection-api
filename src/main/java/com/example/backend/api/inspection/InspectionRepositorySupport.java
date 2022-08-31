package com.example.backend.api.inspection;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.InspectionDto;
import com.example.backend.api.inspection.model.QInspection;
import com.example.backend.api.inspection.question.model.QQuestion;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InspectionRepositorySupport extends QuerydslRepositorySupport{

    private final JPAQueryFactory jpaQueryFactory;

    public InspectionRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Inspection.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QInspection inspection = QInspection.inspection;

    public List<Inspection> findAll(InspectionDto.Request params){
        List<Inspection> inspectionList = jpaQueryFactory
                .selectFrom(inspection)
                .where(octagnosisYnEq(params.getOctagnosisYn()))
                .fetch();

        return inspectionList;
    }

    public Inspection findByInspectionIdx(int inspectionIdx){
        Inspection inspectionDetail = jpaQueryFactory
                .selectFrom(inspection)
                .where(inspection.inspectionIdx.eq(inspectionIdx))
                .fetchOne();

        return inspectionDetail;
    }

    public void updateInspection(InspectionDto.Request params, int inspectionIdx){
        jpaQueryFactory
                .update(inspection)
                .set(inspection.inspectionName, params.getInspectionName())
                .set(inspection.rankCount, params.getRankCount())
                .set(inspection.payYn, params.getPayYn())
                .set(inspection.showYn, params.getShowYn())
                .where(inspection.inspectionIdx.eq(inspectionIdx))
                .execute();
    }

    private BooleanExpression octagnosisYnEq(String octagnosisYn){
        return octagnosisYn != null ? inspection.octagnosisYn.eq(octagnosisYn) : null;
    }
}
