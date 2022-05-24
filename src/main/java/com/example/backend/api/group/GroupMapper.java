package com.example.backend.api.group;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMapper {

    List<Group> getGroupList(String searchText);
    Group getGroupDetail(int idx);
    void insertGroup(Group group);
    void updateGroup(@Param("groupIdx") int groupIdx, @Param("group") Group group);
    void deleteGroup(int groupIdx);

}
