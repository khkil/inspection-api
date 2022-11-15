package com.example.backend.api.inspection.rank;

import com.example.backend.api.inspection.InspectionService;
import com.example.backend.api.inspection.model.InspectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RankService {

    @Autowired
    RankMapper rankMapper;

    @Autowired
    InspectionService inspectionService;
    public List<Rank> getMemberInspectionRank(int memberIdx, int inspectionIdx){
        InspectionDto.Detail inspectionDetail = inspectionService.getInspectionDetail(inspectionIdx);
        int rankCount = inspectionDetail.getRankCount();
        return rankMapper.getMemberInspectionRank(memberIdx, inspectionIdx, rankCount);
    }

}
