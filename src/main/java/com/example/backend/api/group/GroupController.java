package com.example.backend.api.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity<List> getGroupList(@RequestParam(required = false) String searchText) {
        return new ResponseEntity<>(groupService.getGroupList(searchText), HttpStatus.OK);
    }

    @GetMapping("/{idx}")
    public ResponseEntity getGroupDetail(@PathVariable int idx){
        return ResponseEntity.ok(groupService.getGroupDetail(idx));

    }

    @PostMapping
    public ResponseEntity insertGroup(@RequestBody Group group){

        groupService.insertGroup(group);
        return ResponseEntity.ok(group);
    }
}
