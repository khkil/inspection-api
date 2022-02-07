package com.example.backend.api.inspection.rank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RankService {

    @Autowired
    RankMapper rankMapper;
    public List<Rank> getMemberInspectionRank(int memberIdx, int inspectionIdx){
        return rankMapper.getMemberInspectionRank(memberIdx, inspectionIdx);
    }

}
