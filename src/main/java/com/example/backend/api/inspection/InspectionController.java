package com.example.backend.api.inspection;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.InspectionDto;
import com.example.backend.api.inspection.progress.ProgressDto;
import com.example.backend.api.inspection.progress.ProgressService;
import com.example.backend.api.member.model.Member;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    @Autowired
    InspectionService inspectionService;
    @Autowired
    ProgressService progressService;

    @GetMapping("")
    public ResponseEntity getInspectionList(InspectionDto.Request inspection) {

        List<InspectionDto.Summary> inspectionList = inspectionService.getInspectionList(inspection);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionList));
    }


    @GetMapping("/{inspectionIdx}")
    public ResponseEntity getInspectionDetail(@PathVariable int inspectionIdx) {

        InspectionDto.Detail inspectionDetail = inspectionService.getInspectionDetail(inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionDetail));
    }

    @GetMapping("/progress")
    public ResponseEntity getMemberProgressList(@AuthenticationPrincipal Member member, @PathVariable int inspectionIdx) {

        ProgressDto memberProgressDetail = progressService.getMemberProgressDetail(member.getIdx(), inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgressDetail));
    }

    @GetMapping("/{inspectionIdx}/progress")
    public ResponseEntity getMemberProgressDetail(@AuthenticationPrincipal Member member, @PathVariable int inspectionIdx) {

        ProgressDto memberProgressDetail = progressService.getMemberProgressDetail(member.getIdx(), inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgressDetail));
    }



}
