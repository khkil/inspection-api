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
import java.util.List;

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
    public ResponseEntity uploadFromStorage(@RequestPart(value="files", required = false) List<MultipartFile> files, @RequestParam(value="directory") String directory){
        fileService.uploadFileFromGCS(files, directory);
        return ResponseEntity.ok(CommonResponse.successResult());
    }


}
