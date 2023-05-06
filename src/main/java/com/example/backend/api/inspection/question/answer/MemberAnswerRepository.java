package com.example.backend.api.inspection.question.answer;

import com.example.backend.api.inspection.question.answer.model.MemberAnswer;
import com.example.backend.api.inspection.question.answer.model.MemberAnswerID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MemberAnswerRepository extends CrudRepository<MemberAnswer, MemberAnswerID> {

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM member_answer WHERE member_idx = ?1\n" +
                    "AND question_idx IN (\n" +
                    "    SELECT question_idx FROM question WHERE inspection_idx = ?2\n" +
                    ");")
    void deteleMemberAnswers(int memberIdx, int inspectionIdx);
}
