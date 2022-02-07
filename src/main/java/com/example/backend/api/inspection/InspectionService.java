package com.example.backend.api.inspection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InspectionService {

    @Autowired
    InspectionMapper inspectionMapper;

    public List<Inspection> getInspectionList(Inspection inspection){
        return inspectionMapper.getInspectionList(inspection);
    }

    public Inspection getInspectionDetail(int inspectionIdx){
        return inspectionMapper.getInspectionDetail(inspectionIdx);
    }

}
