package com.example.backend.api.group;

import com.example.backend.api.group.code.GroupCodeConfig;
import com.example.backend.api.inspection.model.Inspection;
import lombok.Data;

import java.util.List;

@Data
public class Group {

    private int idx;
    private String name;
    private String tel;
    private String address;
    private String addressSub;
    private String contactName;
    private String contactEmail;
    private String contactTel;
    private String groupCode;

    private GroupCodeConfig groupCodeConfig;
    private List<Integer> grades;
    private List<Inspection> inspections;
}