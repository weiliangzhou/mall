package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserCertificationMapper;
import com.zwl.model.po.UserCertification;
import com.zwl.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@SuppressWarnings("All")
@Service
public class CertificationServiceImpl implements CertificationService {
    @Autowired
    private UserCertificationMapper userCertificationMapper;
    @Override
    public int add(UserCertification userCertification) {
        return userCertificationMapper.insert(userCertification);
    }

    @Override
    public int modifyById(UserCertification userCertification) {
        return userCertificationMapper.updateById(userCertification);
    }

    @Override
    public int modifyByUserId(UserCertification userCertification) {
        return userCertificationMapper.updateByUserId(userCertification);
    }
    @Override
    public UserCertification getOneByUserId(String userId) {
        /*//用户还未提交实名认证
        List<UserCertification> userCertificationList = userCertificationMapper.selectListByUserId(userId);
        if(CheckUtil.isEmpty(userCertificationList)){
            UserCertification userCertification=new UserCertification();
            userCertification.setStatus(0);
            return  userCertification;
        }*/
        //查询是否存在以及提交实名认证，并审核通过
        UserCertification queryUserCertification = new UserCertification();
        queryUserCertification.setUserId(userId);
        queryUserCertification.setStatus(2);
        UserCertification userCertification2 = userCertificationMapper.getOneByParams(queryUserCertification);
        if (userCertification2 != null) {
            return userCertification2;
        }
        //查询是否已经提交了实名认证，还在审核中状态
        queryUserCertification.setStatus(1);
        UserCertification userCertification1 = userCertificationMapper.getOneByParams(queryUserCertification);
        if (userCertification1 != null) {
            return userCertification1;
        }
        //查询是否已经提交了实名认证，已经被驳回状态
        queryUserCertification.setStatus(3);
        UserCertification userCertification3 = userCertificationMapper.getOneByParams(queryUserCertification);
        if (userCertification3 != null) {
            return userCertification3;
        }
        //用户还未提交实名认证
        UserCertification userCertification=new UserCertification();
        userCertification.setStatus(0);
        return userCertification;
    }

    @Override
    public List<UserCertification> getListByMerchantId(String merchantId) {
        return userCertificationMapper.selectListByMerchantId(merchantId);
    }

    @Override
    public UserCertification getById(Long id) {
        return userCertificationMapper.selectById(id);
    }

    @Override
    public List<UserCertification> getListByStatus(String merchantId, Integer status) {
        return userCertificationMapper.selectListByStatus(merchantId, status);
    }

    @Override
    public UserCertification getOneByParams(UserCertification userCertification) {
        return userCertificationMapper.getOneByParams(userCertification);
    }

    @Override
    public List<UserCertification> getListByUserId(String userId) {
        return userCertificationMapper.selectListByUserId(userId);
    }
}
