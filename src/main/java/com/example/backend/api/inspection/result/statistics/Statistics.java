package com.example.backend.api.inspection.result.statistics;

import lombok.Data;

@Data
public class Statistics {

/*    private User user;
    private Group group;
    private Question question;
    private Result result;*/
    private int userIdx;
    private String userName;
    private int userGrade;
    private String userEtc;
    private int groupIdx;
    private String groupName;
    private int resultIdx;
    private String resultName;
    private int resultScore;
}
