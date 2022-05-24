package com.example.backend.api.group.code;

import lombok.Data;

@Data
public class GroupCode {

    private int codeIdx;
    private int groupIdx;
    private String code;
    private int maxCount;
    private String createdDate;

}
