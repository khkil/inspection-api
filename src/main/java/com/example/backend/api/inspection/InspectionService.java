package com.example.backend.api.inspection;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.InspectionDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InspectionService {

    @Autowired
    InspectionRepositorySupport inspectionRepositorySupport;

    @Autowired
    ModelMapper modelMapper;

    public List<InspectionDto.Summary> getInspectionList(InspectionDto.Request inspection){
        List<Inspection> inspectionList = inspectionRepositorySupport.findAll(inspection);
        return inspectionList.stream().map(v -> new InspectionDto.Summary(v)).collect(Collectors.toList());
    }

    public InspectionDto.Detail getInspectionDetail(int inspectionIdx){
        InspectionDto.Detail inspectionDetail = modelMapper.map(inspectionRepositorySupport.findByInspectionIdx(inspectionIdx), InspectionDto.Detail.class);
        return inspectionDetail;
    }

}
