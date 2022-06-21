package com.example.backend.api.group.code;

import com.example.backend.api.group.Group;
import com.example.backend.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupCodeService {

    @Autowired
    GroupCodeMapper groupCodeMapper;

    public GroupCodeConfig getGroupCodeConfig(int groupIdx){
        return groupCodeMapper.getGroupCodeConfig(groupIdx);
    }

    public void saveGroupCodeConfig(int groupIdx, GroupCodeConfig groupCodeConfig){
        groupCodeMapper.saveGroupCodeConfig(groupIdx, groupCodeConfig);
    }

    public Group checkCode(String groupCode){
        Group group = groupCodeMapper.getGroupDetailFromCode(groupCode);
        if(group == null){
            throw new ApiException("유효하지 않은 코드 입니다");
        }
        int groupIdx = group.getIdx();
        if(!checkCount(groupIdx)){
            throw new ApiException("등록인원을 초과하였습니다");
        }
        return groupCodeMapper.getGroupDetailFromCode(groupCode);
    }

    public boolean checkCount(int groupIdx){
        GroupCodeConfig groupCodeConfig = groupCodeMapper.getGroupCodeConfig(groupIdx);
        int maxCount = groupCodeConfig.getMaxCount();
        int memberCount = groupCodeMapper.getMemberCount(groupIdx);
        boolean checkSuccess = maxCount > memberCount;
        return checkSuccess;

    }
}
