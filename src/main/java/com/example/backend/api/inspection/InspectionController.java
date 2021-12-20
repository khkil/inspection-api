package com.example.backend.api.inspection;

import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    @Autowired
    InspectionServcice inspectionServcice;

    @GetMapping("")
    public ResponseEntity getInspectionList(Inspection inspection) {

        List<Inspection> inspectionList = inspectionServcice.getInspectionList(inspection);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionList));
    }


    @GetMapping("/{inspectionIdx}")
    public ResponseEntity getInspectionDetail(@PathVariable int inspectionIdx) {

        Inspection inspectionDetail = inspectionServcice.getInspectionDetail(inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionDetail));
    }

    @PutMapping
    public ResponseEntity<?> updateInspection(@RequestBody Inspection inspection) {
        return null;
    }

}
