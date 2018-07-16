package com.zwl.service;

import com.zwl.model.po.UserInfo;

/**
 * 用户详情service
 */
public interface UserInfoService {
    /**
     * 新增用户详情
     * @param userInfo
     */
    void add(UserInfo userInfo);

    /**
     * 修改用户详情
     * @param userInfo
     */
    void modifyByParams(UserInfo userInfo);
    /**
     * 根据userId获取用户详情
     */
    UserInfo getByUserId(String userId);
}
