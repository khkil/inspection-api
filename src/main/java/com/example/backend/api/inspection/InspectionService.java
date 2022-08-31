package com.example.backend.api.inspection;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.InspectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InspectionService {

    @Autowired
    InspectionRepositorySupport inspectionRepositorySupport;

    public List<InspectionDto.Summary> getInspectionList(InspectionDto.Request inspection){
        List<Inspection> inspectionList = inspectionRepositorySupport.findAll(inspection);
        return inspectionList.stream().map(v -> new InspectionDto.Summary(v)).collect(Collectors.toList());
    }

    public InspectionDto.Summary getInspectionDetail(int inspectionIdx){
        Inspection inspectionDetail = inspectionRepositorySupport.findByInspectionIdx(inspectionIdx);
        return new InspectionDto.Summary(inspectionDetail);
    }

}
