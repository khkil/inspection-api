package com.example.backend.util;

import com.example.backend.api.util.gcs.model.DownloadReq;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Component
public class GoogleCloudStorage {

    private final String BUCKET_NAME = "careercompany";

    @Autowired
    Storage storage;

    public Blob downloadInfo(DownloadReq downloadReq){
        try {
            Blob blob = storage.get(downloadReq.getBucketName(), downloadReq.getDownloadFileName());
            blob.downloadTo(Paths.get(downloadReq.getLocalFileLocation()));
            return blob;
        } catch(IllegalStateException e){
            throw new RuntimeException(e);
        }
    }

    public BlobInfo uploadInfo(MultipartFile file) {
        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(BUCKET_NAME, file.getOriginalFilename()).build(), //get original file name
                    file.getBytes(), // the file
                    BlobTargetOption.predefinedAcl(PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            return blobInfo;
        }catch(IllegalStateException | IOException e){
            throw new RuntimeException(e);
        }
    }
}