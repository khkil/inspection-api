package com.example.backend.api.util.gcs;

import com.example.backend.api.util.gcs.model.DownloadReq;
import com.example.backend.api.util.gcs.model.File;
import com.example.backend.util.GoogleCloudStorage;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    @Autowired
    GoogleCloudStorage googleCloudStorageBean;

    public File downloadFileFromGCS(DownloadReq downloadReq) {

        Blob blob = googleCloudStorageBean.downloadInfo(downloadReq);
        String fileName = downloadReq.getDownloadFileName();
        String fileUrl = blob.getMediaLink();
        return new File(fileName, fileUrl);
    }

    public File uploadFileFromGCS(MultipartFile multipartFile){

        BlobInfo blobInfo = googleCloudStorageBean.uploadInfo(multipartFile);
        String fileName = multipartFile.getName();
        String fileUrl = blobInfo.getMediaLink();

        return new File(fileName, fileUrl);
    }


}
