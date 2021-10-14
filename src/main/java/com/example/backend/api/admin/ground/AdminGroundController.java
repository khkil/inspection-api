package com.example.backend.api.admin.ground;

import com.example.backend.api.result.statistics.StatisticsService;
import com.example.backend.api.user.User;
import com.example.backend.api.user.UserAnswer;
import com.example.backend.api.user.UserServcice;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.ExcelGenerator;
import com.github.pagehelper.PageHelper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/ground")
public class AdminGroundController {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    UserServcice userServcice;

    @GetMapping("/excel/statistics")
    public ResponseEntity downStatisticsExcel(@RequestParam(required = true) int inspectionIdx) throws IOException {

        JSONObject data = statisticsService.getGroundStatistics(inspectionIdx);
        ByteArrayInputStream in = ExcelGenerator.groundStatisticsExcel(data);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=statistics.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));

    }

    @GetMapping("/excel/statistics/users/{userIdx}")
    public void downPrivateStatisticsExcel(@PathVariable int userIdx, HttpServletResponse response) throws IOException {

        User user = userServcice.getUserDetail(userIdx);
        List<UserAnswer> userAnswers = userServcice.getUserDetailAnswers(userIdx);
        ExcelGenerator.groundPersonalStatisticsExcel(user, userAnswers, response);
    }
}
