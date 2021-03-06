package com.zwl.service;

import com.zwl.model.po.UserQuotaCount;

/**
 * @author 二师兄超级帅
 * @Title: UserQuotaCountService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1415:41
 */
public interface UserQuotaCountService {
    int updateCountByUserId(String referrerId);
    int saveOrUpdate(String userId, int i);
    UserQuotaCount getByUserId(String userId);
}
