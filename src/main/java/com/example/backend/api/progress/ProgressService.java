package com.example.backend.api.progress;

import com.example.backend.api.inspection.Inspection;
import com.example.backend.api.progress.model.Progress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    @Autowired
    ProgressMapper progressMapper;

    public List<Progress> getMyProgressList(int memberIdx, Inspection inspection){
        return progressMapper.getMemberProgressList(memberIdx, inspection);
    }

    public Progress getMemberProgressDetail(int memberIdx, int inspectionIdx){
        return progressMapper.getMemberProgressDetail(memberIdx, inspectionIdx);
    }

    public void deleteMemberProgress(int memberIdx, int inspectionIdx){
        progressMapper.deleteMemberProgress(memberIdx, inspectionIdx);
    }
}
