package com.example.backend.api.admin;

import com.example.backend.api.inspection.InspectionService;
import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.model.InspectionDto;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inspections")
public class AdminInspectionController {

    @Autowired
    InspectionService inspectionService;

    @GetMapping
    public ResponseEntity getInspectionList(Inspection inspection) throws  Exception{

        List<InspectionDto.Response> inspectionList = inspectionService.getInspectionList(inspection);
        return ResponseEntity.ok(CommonResponse.successResult(inspectionList));
    }


    @GetMapping("/{idx}")
    public ResponseEntity getInspectionDetail(@PathVariable int idx){

        InspectionDto.Response inspectionDetail = inspectionService.getInspectionDetail(idx);
        if(inspectionDetail == null){
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(CommonResponse.successResult(inspectionDetail));
    }

    @PutMapping
    public ResponseEntity<?> updateInspection(@RequestBody Inspection inspection){
        return null;
    }

}
