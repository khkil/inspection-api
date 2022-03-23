package com.example.backend.api.progress;

import com.example.backend.api.inspection.Inspection;
import com.example.backend.api.progress.model.Progress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProgressMapper {

    List<Progress> getMemberProgressList(int memberIdx);
    Progress getMemberProgressDetail(int memberIdx, int inspectionIdx);
    void deleteMemberProgress(int memberIdx, int inspectionIdx);

}
