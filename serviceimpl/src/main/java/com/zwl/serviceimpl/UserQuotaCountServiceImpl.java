package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserQuotaCountMapper;
import com.zwl.service.UserQuotaCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二师兄超级帅
 * @Title: 用户限定名额
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1415:41
 */
@Service
public class UserQuotaCountServiceImpl implements UserQuotaCountService {
    @Autowired
    private UserQuotaCountMapper userQuotaCountMapper;

    @Override
    public Integer getCountByUserId(String userId) {
        return userQuotaCountMapper.getCountByUserId(userId);
    }

    @Override
    public int updateCountByUserId(String userId) {
        return userQuotaCountMapper.updateCountByUserId(userId);
    }

    @Override
    public int saveOrUpdate(String userId, int i) {
        return userQuotaCountMapper.saveOrUpdate(userId, i);
    }
}
