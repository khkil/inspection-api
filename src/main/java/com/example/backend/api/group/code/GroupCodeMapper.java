package com.example.backend.api.group.code;

import com.example.backend.api.group.Group;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GroupCodeMapper {

    GroupCodeConfig getGroupCodeConfig(int groupIdx);
    void updateGroupCodeConfig(int groupIdx, GroupCodeConfig groupCodeConfig);
    Group getGroupDetailFromCode(String groupCode);
}
