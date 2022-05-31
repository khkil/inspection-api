package com.example.backend.api.group.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupCodeService {

    @Autowired
    GroupCodeMapper groupCodeMapper;

    public GroupCodeConfig getGroupCodeConfig(int groupIdx){
        return groupCodeMapper.getGroupCodeConfig(groupIdx);
    }
}
