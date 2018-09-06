package com.zwl.service;


import com.zwl.model.baseresult.Result;
import com.zwl.model.po.User;
import com.zwl.model.vo.H5LoginResultVo;
import com.zwl.model.vo.UserLoginInfoVo;
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
     * 根据userId获取用户信息
     *
     * @param userId
     * @return
     */
    User getByUserId(String userId);

    /**
     * 根据用户参数动态获取（openid等）
     *
     * @param user
     * @return
     */
    User getOneByParams(User user);

    /**
     * 根据主键userId更新
     *
     * @param user
     * @return
     */
    int updateUserByUserId(User user);

    /**
     * 根据用户参数获取用户list
     *
     * @param user
     * @return
     */
    List<User> getListByParams(User user);

    /**
     * 获取用户列表
     *
     * @param merchantId
     * @param queryType:普通会员列表 0
     * @param queryType:付费会员列表 1
     * @return
     */
    List<User> getUserListByMerchantId(UserQueryVo userQueryVo);


    /**
     * 搜索
     *
     * @return
     */
    List<User> search(String merchantId, String registerMobile, Integer registerFrom);

    Integer getMemberLevel(String userId);

    Integer getMaidPercentByUserId(String userId);

    /**
     * 保存授勸登錄信息
     *
     * @param userLoginInfoVo
     * @param openid
     * @return
     */
    String saveAuthorization(UserLoginInfoVo userLoginInfoVo, String openid);

    /**
     * 更新授權登錄信息
     *
     * @param userLoginInfoVo
     */
    void modifyAuthorization(UserLoginInfoVo userLoginInfoVo, User userQuery);

    /**
     * 根据userId获取推荐人信息
     *
     * @param userId
     * @return
     */
    User getReferrerByUserId(String userId);

    /**
     * 根据userId获取总业绩
     *
     * @param userIdTemp
     * @return
     */
    Integer getTotalPerformanceByUserId(String userIdTemp);

    /**
     * 通过微信openId 跟商户的编号查询用户
     *
     * @param openId     微信openId
     * @param merchantId 商户的编号
     * @return 用户
     */
    User getUserByOpenIdAndMerchantId(String openId, String merchantId);

    /**
     * 根据公众号跟商户编号查询授权用户
     *
     * @param gzhOpenId  公众号
     * @param merchantId 商户编号
     */
    User getUserByGzhOpenIdAndMerchantId(String gzhOpenId, String merchantId);

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     */
    User getUserByPhone(String phone);

    /**
     * 修改用户手机号
     *
     * @param userId 用户编号
     * @param phone  手机号码
     * @return
     */
    Boolean updateUserPhoneByUserId(String userId, String phone);

    /**
     * 修改用户手机号
     *
     * @param userId    用户编号
     * @param gzhOpenId 用户公众号
     * @return
     */
    Boolean updateUserGzhOpenIdByUserId(String userId, String gzhOpenId);

    /**
     * 小程序登录
     *
     * @param userLoginInfoVo
     * @param code            微信授权code
     * @param merchantId      商户APP编号用于获取APPID APPKEY
     */
    Result miniAppWeChatAuthorization(UserLoginInfoVo userLoginInfoVo, String code, String merchantId);

    /**
     * h5获取用户使用权限
     *
     * @param userLoginInfoVo
     * @param code            微信授权code
     * @param merchantId      商户APP编号用于获取APPID APPKEY
     */
    @Deprecated
    Result h5WeChatAuthorization(UserLoginInfoVo userLoginInfoVo, String code, String merchantId);

    /**
     * @param phone          手机号码
     * @param msgCode        短信验证码
     * @param gzhOpenId      公众号openid
     * @param merchantId     商户编号
     * @param wxAccreditCode h5微信授权code
     */
    H5LoginResultVo h5WeChatLogin(String phone, String msgCode, String gzhOpenId, String merchantId, String wxAccreditCode);
}
