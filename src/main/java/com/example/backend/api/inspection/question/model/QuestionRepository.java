package com.example.backend.api.inspection.question.model;

import com.example.backend.api.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {



}
