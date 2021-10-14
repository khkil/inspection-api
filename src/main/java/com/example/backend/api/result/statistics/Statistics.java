package com.example.backend.api.result.statistics;

import com.example.backend.api.group.Group;
import com.example.backend.api.question.Question;
import com.example.backend.api.result.Result;
import com.example.backend.api.user.User;
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
