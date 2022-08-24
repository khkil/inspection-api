package com.example.backend.api.inspection;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.InspectionDto;
import com.example.backend.api.inspection.result.InspectionRepositorySupport;
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

    public List<InspectionDto.Response> getInspectionList(Inspection inspection){
        List<Inspection> inspectionList = inspectionRepositorySupport.findAll();
        return inspectionList.stream().map(v -> new InspectionDto.Response(v)).collect(Collectors.toList());
        //return inspectionMapper.getInspectionList(inspection);
    }

    public InspectionDto.Response getInspectionDetail(int inspectionIdx){
        Inspection inspectionDetail = inspectionRepositorySupport.findByInspectionIdx(inspectionIdx);
        return new InspectionDto.Response(inspectionDetail);
    }

}
