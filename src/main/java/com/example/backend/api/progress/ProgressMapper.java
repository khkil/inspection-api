package com.example.backend.api.progress;

import com.example.backend.api.inspection.Inspection;
import com.example.backend.api.progress.model.Progress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProgressMapper {

    List<Progress> getMemberProgressList(int memberIdx, Inspection inspection);
    Progress getMemberProgressDetail(int memberIdx, int inspectionIdx);
    void deleteMemberProgress(int memberIdx, int inspectionIdx);

}
