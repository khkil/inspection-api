package com.example.backend.api.group.code;

import com.example.backend.api.group.Group;
import com.example.backend.api.group.GroupMapper;
import com.example.backend.common.exception.ApiException;
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

    public void updateGroupCodeConfig(int groupIdx, GroupCodeConfig groupCodeConfig){
        groupCodeMapper.updateGroupCodeConfig(groupIdx, groupCodeConfig);
    }

    public Group getGroupCodeFromCode(String groupCode){
        Group group = groupCodeMapper.getGroupDetailFromCode(groupCode);
        if(group == null){
            throw new ApiException("유효하지 않은 코드 입니다");
        }
        return groupCodeMapper.getGroupDetailFromCode(groupCode);
    }
}
