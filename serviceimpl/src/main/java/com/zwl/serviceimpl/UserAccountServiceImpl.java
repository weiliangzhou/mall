package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserAccountMapper;
import com.zwl.model.po.UserAccount;
import com.zwl.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二师兄超级帅
 * @Title: UserAccountServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1311:48
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public void addBanlanceByUserId(String userId, Integer newMaidMoney) {
        userAccountMapper.addBanlanceByUserId(userId, newMaidMoney);
    }

    @Override
    public Integer getBalanceByUserId(String userId) {
        return userAccountMapper.getBalanceByUserId(userId);
    }

    @Override
    public UserAccount getUserAccountByUserId(String userId) {
        return userAccountMapper.getUserAccountByUserId(userId);
    }

    @Override
    public int save(UserAccount userAccount) {
        return userAccountMapper.insertSelective(userAccount);
    }
}
