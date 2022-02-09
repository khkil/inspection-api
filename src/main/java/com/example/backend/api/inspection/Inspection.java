package com.example.backend.api.inspection;

import lombok.Data;

@Data
public class Inspection {

    private int inspectionIdx;
    private String inspectionName;
    private String payYn;
    private String octagnosisYn;
    private String showYn;
    private int rankCount;
    private int totalPage;

}
