package com.example.backend.api.inspection;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionMapper {

    List<Inspection> getInspectionList(Inspection inspection);
    Inspection getInspectionDetail(int idx);

}
