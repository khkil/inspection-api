package com.example.backend.api.inspection.result;

import com.example.backend.api.inspection.result.model.Result;
import com.example.backend.api.inspection.result.model.ResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<ResultDto.Summary> findAllByInspectionIdx(int inspectionIdx);
}
