package com.example.backend.api.util.gcs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DownloadReq {
    private String bucketName;
    private String downloadFileName;
    private String UploadFileName;
    private String localFileLocation;
}
