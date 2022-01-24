package com.example.backend.api.inspection.result.statistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StatisticsMapper {

    List<Statistics> getGroundStatistics(@Param("inspectionIdx") int inspectionIdx);

}
