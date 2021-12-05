package com.example.backend.api.admin;

import com.example.backend.api.group.Group;
import com.example.backend.api.group.GroupService;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/groups")
public class AdminGroupController {


    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity getAdminGroups(PageUtil pageUtil) {

        PageHelper.startPage(pageUtil.getPageNum(), pageUtil.getPageSize());
        List<Group> groups = groupService.getAdminGroupList();
        PageInfo<Group> pageInfo = new PageInfo<>(groups);
        return ResponseEntity.ok(pageInfo);
    }

    @GetMapping("/{groupIdx}")
    public ResponseEntity getAdminGroupDetail(@PathVariable int groupIdx){
        Group group = groupService.getGroupDetail(groupIdx);
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{groupIdx}")
    public ResponseEntity updateGroup(@PathVariable int groupIdx, @RequestBody Group group){
        groupService.updateGroup(groupIdx, group);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @DeleteMapping("/{groupIdx}")
    public ResponseEntity deleteGroup(@PathVariable int groupIdx){
        groupService.deleteGroup(groupIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

}