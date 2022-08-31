package com.example.backend.api.inspection;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.InspectionDto;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/inspections")
public class InspectionController {

    @Autowired
    InspectionService inspectionService;

    @GetMapping("")
    public ResponseEntity getInspectionList(InspectionDto.Request inspection) {

        List<InspectionDto.Summary> inspectionList = inspectionService.getInspectionList(inspection);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionList));
    }


    @GetMapping("/{inspectionIdx}")
    public ResponseEntity getInspectionDetail(@PathVariable int inspectionIdx) {

        InspectionDto.Summary inspectionDetail = inspectionService.getInspectionDetail(inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionDetail));
    }

    @GetMapping("/{inspectionIdx}/result")
    public ResponseEntity getInspectionResult(@PathVariable int inspectionIdx) {
        return null;
    }

    @PutMapping
    public ResponseEntity<?> updateInspection(@RequestBody Inspection inspection) {
        return null;
    }

}
