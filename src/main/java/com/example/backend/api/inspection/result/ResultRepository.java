package com.example.backend.api.inspection.result;

import com.example.backend.api.inspection.result.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByInspectionIdx(int inspectionIdx);
}
