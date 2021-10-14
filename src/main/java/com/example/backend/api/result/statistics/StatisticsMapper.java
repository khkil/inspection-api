package com.example.backend.api.result.statistics;

import com.example.backend.api.question.Question;
import com.example.backend.api.result.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StatisticsMapper {

    List<Statistics> getGroundStatistics(@Param("inspectionIdx") int inspectionIdx);

}
