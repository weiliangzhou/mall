package com.zwl.dao.mapper;

import com.zwl.model.po.UserReceivingAddress;

public interface UserReceivingAddressMapper {
    int insertSelective(UserReceivingAddress record);
    UserReceivingAddress selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(UserReceivingAddress record);
}