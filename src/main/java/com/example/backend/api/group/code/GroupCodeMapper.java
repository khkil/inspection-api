package com.example.backend.api.group.code;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GroupCodeMapper {

    List<GroupCodeConfig> getGroupCodeList(int groupIdx);
}
