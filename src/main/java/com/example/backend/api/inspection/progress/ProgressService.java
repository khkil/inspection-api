package com.example.backend.api.inspection.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {


    @Autowired
    ProgressRepository progressRepository;

    public ProgressDto getMemberProgressDetail(int inspectionIdx, int memberIdx){

        return progressRepository.findByInspectionIdxAndMemberIdx(inspectionIdx, memberIdx);

    }
}
