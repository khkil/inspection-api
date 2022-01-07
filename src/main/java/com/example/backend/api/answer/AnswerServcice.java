package com.example.backend.api.answer;

import com.example.backend.api.answer.model.MemberAnswer;
import com.example.backend.api.inspection.Inspection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AnswerServcice {

    @Autowired
    AnswerMapper answerMapper;

    public void insertMemberAnswer(int memberIdx, Map<String, Integer> answerMap){
        answerMapper.insertMemberAnswer(memberIdx, answerMap);
    }
}
