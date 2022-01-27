package com.example.backend.api.progress.model;

import lombok.Data;

@Data
public class Progress {

    private int inspectionIdx;
    private String inspectionName;
    private int userCount;
    private int totalCount;
    private int currentPage;
}
