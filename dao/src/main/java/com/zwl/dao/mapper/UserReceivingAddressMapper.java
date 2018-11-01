package com.zwl.dao.mapper;

import com.zwl.model.po.UserReceivingAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserReceivingAddressMapper {
    int insertSelective(UserReceivingAddress record);

    UserReceivingAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserReceivingAddress record);

    List<UserReceivingAddress> getUserReceivingAddressList(UserReceivingAddress userReceivingAddress);

    void updateIsDefaultByUserId(@Param("userId") String userId, @Param("merchantId") String merchantId);

    int deleteById(Integer id);
}