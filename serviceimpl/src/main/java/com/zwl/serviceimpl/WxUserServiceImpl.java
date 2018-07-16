package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserMapper;
import com.zwl.model.po.User;
import com.zwl.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public Integer getMemberLevel(String userId) {
        return userMapper.getMemberLevel(userId);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userMapper.getUserByUserId(userId);
    }

//    @Override
//    public User getUserById(String userId) {
//        return userMapper.getUserById(userId);
//    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public Integer getMaidPercentByUserId(String userId) {
        return userMapper.getMaidPercentByUserId(userId);
    }
}
