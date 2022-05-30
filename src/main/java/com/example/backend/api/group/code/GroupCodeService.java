package com.example.backend.api.group.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupCodeService {

    @Autowired
    GroupCodeMapper groupCodeMapper;

    public List<GroupCodeConfig> getGroupCodeList(int groupIdx){
        return groupCodeMapper.getGroupCodeList(groupIdx);
    }
}
