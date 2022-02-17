package com.example.backend.api.inspection.rank;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankMapper {

    List<Rank> getMemberInspectionRank(int memberIdx, int inspectionIdx, int rankCount);
}
