package com.zwl.service;

import com.zwl.model.po.User;

/**
 * 微信小程序用户service
 */
public interface WxUserService {
    /**
     * 新增
     */
    void addUser(User user);


    Integer getMemberLevel(String userId);

//    User getUserById(String userId);
    User getUserByUserId(String userId);

    int updateUserById(User user);

    Integer getMaidPercentByUserId(String userId);

}
