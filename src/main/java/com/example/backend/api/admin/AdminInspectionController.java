package com.example.backend.api.admin;

import com.example.backend.api.inspection.Inspection;
import com.example.backend.api.inspection.InspectionServcice;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inspections")
public class AdminInspectionController {

    @Autowired
    InspectionServcice inspectionService;

    @GetMapping
    public ResponseEntity getInspectionList(Inspection inspection) throws  Exception{

        List<Inspection> inspectionList = inspectionService.getInspectionList(inspection);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionList));
    }


    @GetMapping("/{idx}")
    public ResponseEntity<Inspection> getInspectionDetail(@PathVariable int idx){

        Inspection inspectionDetail = inspectionService.getInspectionDetail(idx);
        if(inspectionDetail == null){
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Inspection>(inspectionService.getInspectionDetail(idx), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateInspection(@RequestBody Inspection inspection){
        return null;
    }

}
