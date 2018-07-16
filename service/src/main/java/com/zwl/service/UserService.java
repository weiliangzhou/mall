package com.zwl.service;


import com.zwl.model.po.User;

/**
 * 微信小程序用户service
 */
public interface UserService {
    /**
     * 新增
     */
    void addUser(User user);

    /**
     *  根据userId获取用户信息
     * @param userId
     * @return
     */
    User getByUserId(String userId);

    /**
     * 根据用户参数动态获取（openid等）
     * @param user
     * @return
     */
    User getOneByParams(User user);
}
