package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserCertificationMapper;
import com.zwl.model.UserCertification;
import com.zwl.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public UserCertification getOneByUserId(String userId) {
        return userCertificationMapper.selectOneByUserId(userId);
    }

    @Override
    public List<UserCertification> getListByMerchantId(String merchantId) {
        return userCertificationMapper.selectListByMerchantId(merchantId);
    }
}
