package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserCertificationMapper;
import com.zwl.model.UserCertification;
import com.zwl.service.UserCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCertificationServiceImpl implements UserCertificationService {
    @Autowired
    private UserCertificationMapper userCertificationMapper;
    @Override
    public void addUserCertification(UserCertification userCertification) {
        userCertificationMapper.insert(userCertification);
    }

    @Override
    public void modifyById(UserCertification userCertification) {
        userCertificationMapper.updateById(userCertification);
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
