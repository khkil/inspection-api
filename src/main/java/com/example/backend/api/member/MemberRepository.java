package com.example.backend.api.member;

import com.example.backend.api.inspection.progress.ProgressDto;
import com.example.backend.api.member.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    

    Optional<Member> findById(String id);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByIdx(int idx);

    Page<Member> findByNameContaining(String name, Pageable pageable);

    boolean existsMemberById(String id);

    @Query(nativeQuery = true, value = "SELECT\n" +
            "    i.inspection_idx AS inspectionIdx,\n" +
            "    i.inspection_name AS inspectionName,\n" +
            "    ifnull(a.answer_count, 0) / question_count * 100 AS progress," +
            "    ifnull(a.current_page, 0)  AS currentPage\n" +
            "FROM inspection i\n" +
            "LEFT OUTER JOIN (SELECT inspection_idx, count(*)  AS question_count FROM question WHERE del_yn = 'N' GROUP BY inspection_idx) q\n" +
            "    ON i.inspection_idx = q.inspection_idx\n" +
            "LEFT OUTER JOIN (\n" +
            "    SELECT count(*) AS answer_count, i.inspection_idx, max(question_page) AS current_page\n" +
            "    FROM member_answer ma\n" +
            "    LEFT OUTER JOIN question q ON ma.question_idx = q.question_idx\n" +
            "    LEFT OUTER JOIN inspection i ON q.inspection_idx = i.inspection_idx\n" +
            "    WHERE member_idx = ?1\n" +
            "    GROUP BY i.inspection_idx\n" +
            ") a\n" +
            "    ON q.inspection_idx = a.inspection_idx " +
            "WHERE i.octagnosis_yn = 'Y'")
    List<ProgressDto.Summary> getMemberProgressList(int memberIdx);

    @Query(nativeQuery = true, value = "SELECT (\n" +
            "    (SELECT count(*)\n" +
            "    FROM member_answer ma\n" +
            "    LEFT OUTER JOIN question q\n" +
            "    ON ma.question_idx = q.question_idx\n" +
            "    WHERE inspection_idx = ?2\n" +
            "    AND member_idx = ?1) / (SELECT count(*) FROM question WHERE inspection_idx = ?2)\n" +
            ") * 100 AS progress")
    long getMemberProgress(int memberIdx, int inspectionIdx);

    @Query(nativeQuery = true, value = "SELECT * FROM (" +
            "SELECT IFNULL(MAX(q.question_page), 0) AS currentPage,\n" +
            "       (SELECT MAX(question_page) FROM question WHERE del_yn = 'N' AND inspection_idx = ?2) AS totalPage\n" +
            "FROM question q\n" +
            "LEFT OUTER JOIN  member_answer ma\n" +
            "    ON q.question_idx = ma.question_idx\n" +
            "WHERE q.inspection_idx = ?2\n" +
            "AND ma.member_idx = ?1" +
            ") a")
    ProgressDto.History getMemberProgressHistory(int memberIdx, int inspectionIdx);




}
