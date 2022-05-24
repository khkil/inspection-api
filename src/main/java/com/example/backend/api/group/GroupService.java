package com.example.backend.api.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupService {

    @Autowired
    GroupMapper groupMapper;

    public List<Group> getGroupList(String searchText){
        return groupMapper.getGroupList(searchText);
    }

    public Group getGroupDetail(int idx){
        return groupMapper.getGroupDetail(idx);
    }

    public void insertGroup(Group group) {
        groupMapper.insertGroup(group);
    }

    public void updateGroup(int groupIdx, Group group){
        groupMapper.updateGroup(groupIdx, group);

    }

    public void deleteGroup(int groupIdx){
        groupMapper.deleteGroup(groupIdx);

    }
}
