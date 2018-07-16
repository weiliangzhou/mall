package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserMapper;
import com.zwl.model.po.User;
import com.zwl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 二师兄超级帅 on 2018/4/2.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getByUserId(String userId) {
        return null;
    }

    @Override
    public User getOneByParams(User user) {
        return userMapper.selectOneByParams(user);
    }

}
