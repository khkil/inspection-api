package com.example.backend.api.inspection.question.answer;

import com.example.backend.api.inspection.question.answer.model.MemberAnswer;
import com.example.backend.api.inspection.question.answer.model.MemberAnswerID;
import org.springframework.data.repository.CrudRepository;

public interface MemberAnswerRepository extends CrudRepository<MemberAnswer, MemberAnswerID> {

}
