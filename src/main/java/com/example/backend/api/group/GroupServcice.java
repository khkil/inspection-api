package com.example.backend.api.group;

import com.example.backend.api.inspection.Inspection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupServcice {

    @Autowired
    GroupMapper groupMapper;

    public List<Group> getGroupList(){
        return groupMapper.getGroupList();
    }

    public Group getGroupDetail(int idx){
        return groupMapper.getGroupDetail(idx);
    }

    public void insertGroup(Group group) {
        groupMapper.insertGroup(group);
    }

}
