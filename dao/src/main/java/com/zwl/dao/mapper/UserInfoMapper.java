package com.zwl.dao.mapper;

import com.zwl.model.po.UserInfo;

public interface UserInfoMapper {
    int insert(UserInfo userInfo);

    int updateByParams(UserInfo userInfo);

    UserInfo selectByUserId(String userId);
}