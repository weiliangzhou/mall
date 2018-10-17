package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserMaidPercentMapper;
import com.zwl.model.po.UserMaidPercent;
import com.zwl.service.UserMaidPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二师兄超级帅
 * @Title: UserMaidPercentService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/1618:25
 */
@Service
public class UserMaidPercentServiceImpl implements UserMaidPercentService {
    @Autowired
    private UserMaidPercentMapper userMaidPercentMapper;

    @Override
    public UserMaidPercent getUserMaidPercentByMemberLevelAndMerchantId(Integer memberLevel, String MerchantId) {
        return userMaidPercentMapper.getUserMaidPercentByMemberLevelAndMerchantId(memberLevel, MerchantId);
    }
}
