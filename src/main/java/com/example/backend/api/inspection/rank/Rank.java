package com.example.backend.api.inspection.rank;

import com.example.backend.api.inspection.result.model.Result;
import com.example.backend.api.inspection.result.model.ResultContent;
import lombok.Data;

import java.util.List;

@Data
public class Rank {
    private int resultIdx;
    private String resultName;
    private int totalScore;
    private int ranking;

    private List<ResultContent> resultContents;
}
