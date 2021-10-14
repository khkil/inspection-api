package com.example.backend.api.user;

import com.example.backend.api.question.QuestionService;
import com.example.backend.common.exception.ApiException;
import com.example.backend.config.secutiry.JwtTokenProvider;
import com.example.backend.common.CommonResponse;
import com.example.backend.api.result.UserResult;
import com.example.backend.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserServcice userServcice;

    @Autowired
    QuestionService questionServcice;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @GetMapping("/inspections/{inspection_idx}")
    public ResponseEntity getUsers(@PathVariable int inspection_idx){
        List<User> users = userServcice.getUsers(inspection_idx, new HashMap<>());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/inspections/{inspection_idx}/pages/{page}")
    public ResponseEntity getUsersToPages(@PathVariable int inspection_idx, @PathVariable int page, @RequestParam Map<String,Object> param){
        int perPage = 10;
        String orderBy = PageUtil.orderBy("user_idx", String.valueOf(param.get("order")));
        PageHelper.startPage(page, perPage, orderBy);
        List<User> users = userServcice.getUsers(inspection_idx, param);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return ResponseEntity.ok(pageInfo);
    }

    @GetMapping("/{userIdx}")
    public ResponseEntity getUserDetail(@PathVariable int userIdx){
        User user = userServcice.getUserDetail(userIdx);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userIdx}/answers")
    public ResponseEntity getUserAnswers(@PathVariable int userIdx){

        User user = userServcice.getUserDetail(userIdx);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        if(user ==  null) throw new ApiException("유저정보가 없습니다");
        result.put("answers", userServcice.getUserAnswers(userIdx));
        return ResponseEntity.ok(result);
    }


    @PostMapping("/answers")
    public ResponseEntity<CommonResponse> insertUserAnswers(@RequestBody UserResult userResult) {

        userServcice.insertUserInfo(userResult);
        List<Map<String, Integer>> userAnswers = userResult.getUser_answers();
        int userIdx = userResult.getUser_idx();
        for (Map<String, Integer> answer : userAnswers) {
            int questionIdx = answer.get("question_idx");
            int answerIdx = answer.get("answer_idx");
            userServcice.insertUserAnswers(userIdx, questionIdx, answerIdx);
        }
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user_idx", userIdx);
        return new ResponseEntity<>(CommonResponse.successResult(userInfo), HttpStatus.OK);
    }

    @DeleteMapping("/{userIdx}")
    public ResponseEntity deleteUser(@PathVariable int userIdx){
        userServcice.deleteUser(userIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PutMapping("/{userIdx}")
    public ResponseEntity modifyUser(@PathVariable int userIdx, @RequestBody UserResult userResult){
        User user = userResult.getUser();
        List<UserAnswer> userAnswerList = userResult.getUserAnswerList();
        userServcice.modifyUser(userIdx, user, userAnswerList);
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
