package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserCertificationMapper;
import com.zwl.model.po.UserCertification;
import com.zwl.service.CertificationService;
import com.zwl.util.CheckUtil;
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
        return userCertificationMapper.selectOneByUserId(userId);
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
}
