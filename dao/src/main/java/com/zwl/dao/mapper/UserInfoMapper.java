package com.zwl.dao.mapper;

import com.zwl.model.po.UserInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInfoMapper {
    int insert(UserInfo userInfo);

    int updateByParams(UserInfo userInfo);

    UserInfo selectByUserId(String userId);

    @Select("select user_id from ss_user where referrer = #{userId}")
    List<String> getMyUserByUserId(String userId);
}