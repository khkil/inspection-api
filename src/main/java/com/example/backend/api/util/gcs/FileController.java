package com.example.backend.api.util.gcs;

import com.example.backend.api.util.gcs.model.DownloadReq;
import com.example.backend.api.util.gcs.model.File;
import com.example.backend.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {


    @Autowired
    FileService fileService;

    @PostMapping("/download")
    public ResponseEntity downloadFromStorage(@RequestBody DownloadReq downloadReq){
        File file = fileService.downloadFileFromGCS(downloadReq);
        return ResponseEntity.ok(CommonResponse.successResult(file));
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFromStorage(@RequestPart(value="file", required = false) MultipartFile multipartFile){
        File file = fileService.uploadFileFromGCS(multipartFile);
        return ResponseEntity.ok(CommonResponse.successResult(file));
    }
}
