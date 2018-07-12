package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserInfoMapper;
import com.zwl.model.UserInfo;
import com.zwl.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public void add(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void modifyByParams(UserInfo userInfo) {
        userInfoMapper.updateByParams(userInfo);
    }

    @Override
    public UserInfo getByUserId(String userId) {
        return userInfoMapper.selectByUserId(userId);
    }
}
