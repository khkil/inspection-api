package com.example.backend.api.admin;

import com.example.backend.api.progress.ProgressService;
import com.example.backend.api.progress.model.Progress;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/progress")
public class AdminProgressController {

    @Autowired
    ProgressService progressService;

    @GetMapping("/members/{memberIdx}")
    public ResponseEntity getMemberProgressList(@PathVariable int memberIdx){
        List<Progress> memberProgress = progressService.getMemberProgressList(memberIdx);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgress));
    }
}
