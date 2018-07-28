package com.zwl.service;


import com.zwl.model.po.User;
import com.zwl.model.vo.UserQueryVo;

import java.util.List;

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

    /**
     * 根据主键userId更新
     * @param user
     * @return
     */
    int updateUserByUserId(User user);

    /**
     * 根据用户参数获取用户list
     * @param user
     * @return
     */
    List<User> getListByParams(User user);
    /**
     * 获取用户列表
     * @param merchantId
     * @param queryType:普通会员列表  0
     * @param queryType:付费会员列表  1
     * @return
     */
    List<User> getUserListByMerchantId( UserQueryVo userQueryVo);


    /**
     * 搜索
     * @return
     */
    List<User> search(String merchantId,String registerMobile,Integer registerFrom);

    Integer getMemberLevel(String userId);

    Integer getMaidPercentByUserId(String userId);

}
