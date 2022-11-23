package com.example.backend.api.member;

import com.example.backend.api.inspection.progress.ProgressDto;
import com.example.backend.api.member.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    

    Optional<Member> findById(String id);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByIdx(int idx);

    Page<Member> findByNameContaining(String name, Pageable pageable);

    boolean existsMemberById(String id);

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
