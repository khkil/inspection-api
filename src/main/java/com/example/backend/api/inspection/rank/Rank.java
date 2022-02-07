package com.example.backend.api.inspection.rank;

import lombok.Data;

@Data
public class Rank {
    private String resultName;
    private int totalScore;
    private int ranking;
}
