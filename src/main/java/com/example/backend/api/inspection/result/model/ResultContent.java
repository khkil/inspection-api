package com.example.backend.api.inspection.result.model;

import lombok.Data;

@Data
public class ResultContent {

    private int resultIdx;
    private String contentType;
    private String content;
}
