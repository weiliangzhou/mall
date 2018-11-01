package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserReceivingAddressMapper;
import com.zwl.model.po.UserReceivingAddress;
import com.zwl.service.UserReceivingAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: UserReceivingAddressServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3011:55
 */
@Service
@Slf4j
public class UserReceivingAddressServiceImpl implements UserReceivingAddressService {
    @Autowired
    private UserReceivingAddressMapper userReceivingAddressMapper;

    @Override
    public void insert(UserReceivingAddress userReceivingAddress) {
        int count = userReceivingAddressMapper.insertSelective(userReceivingAddress);
        if (count != 1) {
            log.error("插入收货地址失败!");
        }
    }

    @Override
    public void update(UserReceivingAddress userReceivingAddress) {
        int count = userReceivingAddressMapper.updateByPrimaryKeySelective(userReceivingAddress);
        if (count != 1) {
            log.error("修改收货地址失败!");
        }
    }

    @Override
    public List<UserReceivingAddress> getUserReceivingAddressList(UserReceivingAddress userReceivingAddress) {
        return userReceivingAddressMapper.getUserReceivingAddressList(userReceivingAddress);
    }

    @Override
    public void updateIsDefaultByUserId(String userId, String merchantId) {
        userReceivingAddressMapper.updateIsDefaultByUserId(userId,merchantId);
    }

    @Override
    public UserReceivingAddress getOneById(Long id) {
        return userReceivingAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        int count = userReceivingAddressMapper.deleteById(id);
        if (count != 1) {
            log.error("删除收货地址失败!");
        }
    }
}
