package com.zwl.service;

import com.zwl.model.po.UserCertification;

import java.util.List;

public interface CertificationService {
    /**
     * 新增 用户提交实名认证申请信息
     *
     * @param userCertification
     * @return
     */
    int add(UserCertification userCertification);

    /**
     * 根据插入参数 修改用户实名信息
     *
     * @param userCertification
     * @return
     */
    int modifyById(UserCertification userCertification);
    /**
     * 根据插入参数 修改用户实名信息
     *
     * @param userCertification
     * @return
     */
    int modifyByUserId(UserCertification userCertification);

    /**
     * 根据userId查询用户提交的实名认证信息
     *
     * @param userId
     * @return
     */
    UserCertification getOneByUserId(String userId);

    /**
     * 查找merchantId下的所有用户实名申请信息
     *
     * @param merchantId
     * @return
     */
    List<UserCertification> getListByMerchantId(String merchantId);

    /**
     * 根据Id查询用户提交的实名认证信息
     *
     * @return
     */
    UserCertification getById(Long id);

    /**
     * 根据审核状态查找merchantId下的所有用户实名申请信息
     *
     * @return
     */
    List<UserCertification> getListByStatus(String merchantId,Integer status);
}
