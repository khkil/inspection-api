package com.example.backend.api.user;

import com.example.backend.api.group.Group;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Data
public class User {

    private int userIdx;
    private int inspectionIdx;
    private String userName;
    private String userAge;
    private String userGender;
    private String userGrade;
    private String userEtc;
    private String userDate;
    private int groupIdx;
    private String cdate;
    private String delYn;

    private Group group;

}
