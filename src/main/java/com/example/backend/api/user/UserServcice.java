package com.example.backend.api.user;

import com.example.backend.api.result.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServcice {

    @Autowired
    UserMapper userMapper;

    public List<User> getUsers(int inspection_idx, Map<String, Object> param){
       return userMapper.getUsers(inspection_idx, param);
    }

    public User getUserDetail(int userIdx){
        return userMapper.getUserDetail(userIdx);
    }

    public List<Map<String, Object>> getUserAnswers(int userIdx){
        return userMapper.getUserAnswers(userIdx);
    };
    public List<UserAnswer> getUserDetailAnswers(int userIdx){
        return userMapper.getUserDetailAnswerList(userIdx);
    };

    public void insertUserAnswers(int user_idx, int question_idx, int answer_idx){
        userMapper.insertUserAnswers(user_idx, question_idx, answer_idx);
    }

    public void insertUserInfo(UserResult userResult){
        userMapper.insertUserInfo(userResult);
    }

    public void deleteUser(int userIdx){
        userMapper.deleteUser(userIdx);
    }

    public void modifyUser(int userIdx, User user, List<UserAnswer> userAnswerList ){
        userMapper.modifyUser(userIdx, user, userAnswerList);
    }
}
