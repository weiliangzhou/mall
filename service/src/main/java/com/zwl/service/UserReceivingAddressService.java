package com.zwl.service;

import com.zwl.model.po.UserReceivingAddress;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: UserReceivingAddress
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3011:54
 */
public interface UserReceivingAddressService {
    void insert(UserReceivingAddress userReceivingAddress);

    void update(UserReceivingAddress userReceivingAddress);

    List<UserReceivingAddress> getUserReceivingAddressList(UserReceivingAddress userReceivingAddress);

    void updateIsDefaultByUserId(String userId, String merchantId);

    UserReceivingAddress getOneById(Long id);

    void deleteById(Integer id);
}
