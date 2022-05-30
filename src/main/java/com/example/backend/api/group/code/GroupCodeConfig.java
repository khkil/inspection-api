package com.example.backend.api.group.code;

import lombok.Data;

@Data
public class GroupCodeConfig {

    private int codeIdx;
    private int groupIdx;
    private int maxCount;
    private String createdDate;

}
