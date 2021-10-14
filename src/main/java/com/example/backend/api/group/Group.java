package com.example.backend.api.group;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
public class Group {

    private int idx;
    private String name;
    private String tel;
    private String address;
    private String contactName;
    private String contactEmail;
    private String contactTel;
    private String flag;

    private List<Integer> grades;
}