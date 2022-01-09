package com.example.backend.api.member.model;

import lombok.Data;

@Data
public class MemberProgress {

    private int inspectionIdx;
    private String inspectionName;
    private int userCount;
    private int totalCount;
}
