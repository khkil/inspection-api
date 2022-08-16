package com.example.backend.api.member;

import com.example.backend.api.member.model.Member;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    

    Optional<Member> findById(String id);

    Optional<Member>  findByEmail(String email);

    Optional<Member>  findByIdx(int idx);

    List<Member> findAll();


}
