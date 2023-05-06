package com.example.backend.api.inspection.question.answer;

import com.example.backend.api.inspection.question.answer.model.MemberAnswer;
import com.example.backend.api.inspection.question.answer.model.MemberAnswerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberAnswerService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MemberAnswerRepository memberAnswerRepository;

    public void saveMemberAnswers(int memberIdx, List<MemberAnswerDto> memberAnswerDtoList){
        List<MemberAnswer> memberAnswerList = memberAnswerDtoList.stream().map(memberAnswerDto -> {
            memberAnswerDto.setMemberIdx(memberIdx);
            return memberAnswerDto.toEntity();
        }).collect(Collectors.toList());
        memberAnswerRepository.saveAll(memberAnswerList);
    }

    public void deleteMemberAnswers(int memberIdx, int inspectionIdx) {
        memberAnswerRepository.deteleMemberAnswers(memberIdx, inspectionIdx);
    }
}
