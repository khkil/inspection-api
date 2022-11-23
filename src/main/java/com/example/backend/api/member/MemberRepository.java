package com.example.backend.api.member;

import com.example.backend.api.member.model.Member;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Nullable;
import java.util.List;
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
    long memberProgress(int memberIdx, int inspectionIdx);


}
