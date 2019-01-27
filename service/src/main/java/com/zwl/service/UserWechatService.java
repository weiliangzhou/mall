package com.zwl.service;

import com.zwl.model.po.UserWechat;

/**
 * 用户绑定微信帐号
 */
public interface UserWechatService {
    /**
     * 查询用户绑定的微信帐号
     *
     * @param userId 用户编号
     */
    UserWechat getUserWechatByUserId(String userId);

    /**
     * 添加用户绑定的微信账号
     */
    UserWechat addUserWechat(UserWechat userWechat);

    /**
     * 保存微信账号
     *
     * @param wechatAccount 微信账号
     */
    UserWechat saveUserWechat(String wechatAccount);
}
