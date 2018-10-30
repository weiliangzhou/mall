package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserReceivingAddressMapper;
import com.zwl.model.po.UserReceivingAddress;
import com.zwl.service.UserReceivingAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
