package com.example.backend.api.user;

import com.example.backend.api.result.UserResult;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    List<User> getUsers(int inspection_idx, @Param("param") Map<String, Object> param);
    User getUserDetail(int userIdx);
    List<Map<String, Object>> getUserAnswers(int userIdx);
    List<UserAnswer> getUserDetailAnswerList(int userIdx);
    void insertUserInfo(UserResult userResult);
    void insertUserAnswers(int user_idx, int question_idx, int answer_idx);
    void deleteUser(int userIdx);
    void modifyUser(@Param("userIdx") int userIdx, @Param("user") User user, @Param("userAnswerList") List<UserAnswer> userAnswerList);
}
