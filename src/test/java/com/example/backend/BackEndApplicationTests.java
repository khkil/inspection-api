package com.example.backend;

import com.example.backend.api.result.statistics.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackEndApplicationTests {

    @Autowired
    StatisticsService statisticsService;
    @Test
    void contextLoads() {


        statisticsService.getGroundStatistics(3);
    }

}
