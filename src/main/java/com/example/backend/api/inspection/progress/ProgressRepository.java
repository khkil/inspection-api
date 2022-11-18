package com.example.backend.api.inspection.progress;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProgressRepository  {

    private final EntityManager entityManager;

    public ProgressDto findByInspectionIdxAndMemberIdx(int inspectionIdx, int memberIdx){
        return (ProgressDto) entityManager.createQuery(
                "SELECT i.inspection_name, (ma.answer_count / q.quesction_count * 100) AS progress\n" +
                "FROM inspection i\n" +
                "LEFT OUTER JOIN (SELECT inspection_idx, count(*) AS quesction_count FROM question GROUP BY inspection_idx) q\n" +
                "    ON i.inspection_idx = q.inspection_idx\n" +
                "LEFT OUTER JOIN (\n" +
                "    SELECT\n" +
                "        count(*) AS answer_count,\n" +
                "        ma.member_idx,\n" +
                "        q.inspection_idx\n" +
                "    FROM member_answer ma\n" +
                "    LEFT OUTER JOIN question q ON ma.question_idx = q.question_idx\n" +
                "    AND q.inspection_idx = 4\n" +
                "    WHERE member_idx = 9\n" +
                "    GROUP BY inspection_idx\n" +
                "    ) ma\n" +
                "ON i.inspection_idx = ma.inspection_idx\n" +
                "WHERE i.inspection_idx = 4;").getSingleResult();
    }


}
