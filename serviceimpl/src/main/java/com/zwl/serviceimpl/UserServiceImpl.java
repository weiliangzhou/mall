package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserMapper;
import com.zwl.model.po.User;
import com.zwl.model.vo.UserQueryVo;
import com.zwl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public User getOneByParams(User user) {
        return userMapper.selectOneByParams(user);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public List<User> getListByParams(User user) {
        return userMapper.selectListByParams(user);
    }

    @Override
    public List<User> getUserListByMerchantId(UserQueryVo userQueryVo) {
        return userMapper.getUserListByMerchantId(userQueryVo);
    }

    @Override
    public List<User> search(String merchantId, String registerMobile, Integer registerFrom) {
        return userMapper.search(merchantId, registerMobile, registerFrom);
    }


}
