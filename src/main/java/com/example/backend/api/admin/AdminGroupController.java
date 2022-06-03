package com.example.backend.api.admin;

import com.example.backend.api.group.Group;
import com.example.backend.api.group.GroupService;
import com.example.backend.api.group.code.GroupCodeConfig;
import com.example.backend.api.group.code.GroupCodeService;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/groups")
public class AdminGroupController {


    @Autowired
    GroupService groupService;
    @Autowired
    GroupCodeService groupCodeService;

    @GetMapping
    public ResponseEntity getAdminGroups(@RequestParam(required = false) String searchText, PageUtil pageUtil) {

        PageHelper.startPage(pageUtil.getPageNum(), pageUtil.getPageSize());
        List<Group> groups = groupService.getGroupList(searchText);
        PageInfo<Group> groupsPageInfo = new PageInfo<>(groups);
        return ResponseEntity.ok(CommonResponse.successResult(groupsPageInfo));
    }

    @GetMapping("/{groupIdx}")
    public ResponseEntity getAdminGroupDetail(@PathVariable int groupIdx){
        Group group = groupService.getGroupDetail(groupIdx);
        return ResponseEntity.ok(CommonResponse.successResult(group));
    }

    @PostMapping
    public ResponseEntity insertGroup(@RequestBody Group group){
        groupService.insertGroup(group);
        return ResponseEntity.ok(CommonResponse.successResult(group));
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

    @GetMapping("/{groupIdx}/code-config")
    public ResponseEntity getGroupCodeConfig(@PathVariable int groupIdx){
        GroupCodeConfig groupCodeConfig = groupCodeService.getGroupCodeConfig(groupIdx);
        return ResponseEntity.ok(CommonResponse.successResult(groupCodeConfig));
    }

    @PutMapping("/{groupIdx}/code-config")
    public ResponseEntity updateGroupCodeConfig(@PathVariable int groupIdx, @RequestBody GroupCodeConfig groupCodeConfig){
        groupCodeService.updateGroupCodeConfig(groupIdx, groupCodeConfig);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

}
