package com.zwl.dao.mapper;

import com.zwl.model.UserCertification;

import java.util.List;

public interface UserCertificationMapper {
    /**
     * 新增 用户提交实名认证申请信息
     * @param userCertification
     * @return
     */
    int insert(UserCertification userCertification);
    /**
     * 根据插入参数 修改用户实名信息
     * @param userCertification
     * @return
     */
    int updateById(UserCertification userCertification);

    /**
     * 根据userId查询用户提交的实名认证信息
     * @param userId
     * @return
     */
    UserCertification selectOneByUserId(String userId);
    /**
     * 查找merchantId下的所有用户实名申请信息
     * @param merchantId
     * @return
     */
    List<UserCertification> selectListByMerchantId(String merchantId);

}