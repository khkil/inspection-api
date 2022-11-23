package com.example.backend.api.inspection.progress;

import com.example.backend.api.inspection.InspectionRepositorySupport;
import com.example.backend.api.inspection.model.InspectionDto;
import com.example.backend.api.member.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {


    @Autowired
    InspectionRepositorySupport inspectionRepositorySupport;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;

    public ProgressDto getMemberProgressDetail(int memberIdx, int inspectionIdx){
        InspectionDto.Detail inspectionDetail = modelMapper.map(inspectionRepositorySupport.findByInspectionIdx(inspectionIdx), InspectionDto.Detail.class);
        Long memberProgress = memberRepository.memberProgress(memberIdx, inspectionIdx);
        return new ProgressDto(inspectionDetail.getInspectionName(), memberProgress);

    }
}
