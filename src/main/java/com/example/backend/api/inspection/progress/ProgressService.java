package com.example.backend.api.inspection.progress;

import com.example.backend.api.inspection.InspectionRepositorySupport;
import com.example.backend.api.inspection.model.InspectionDto;
import com.example.backend.api.member.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {


    @Autowired
    InspectionRepositorySupport inspectionRepositorySupport;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ProgressDto.Summary> getMemberProgressList(int memberIdx){
        return memberRepository.getMemberProgressList(memberIdx);

    }

    public ProgressDto getMemberProgressDetail(int memberIdx, int inspectionIdx){
        InspectionDto.Detail inspectionDetail = modelMapper.map(inspectionRepositorySupport.findByInspectionIdx(inspectionIdx), InspectionDto.Detail.class);
        Long memberProgress = memberRepository.getMemberProgress(memberIdx, inspectionIdx);
        ProgressDto progressDto = new ProgressDto(inspectionDetail.getInspectionName(), memberProgress);;
        return progressDto;
    }

    public ProgressDto.History getMemberProgressHistory(int memberIdx, int inspectionIdx){

        return memberRepository.getMemberProgressHistory(memberIdx, inspectionIdx);

    }
}
