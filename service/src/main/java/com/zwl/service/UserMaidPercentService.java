package com.zwl.service;

import com.zwl.model.po.UserMaidPercent;

/**
 * @author 二师兄超级帅
 * @Title: UserMaidPercentService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/1618:24
 */
public interface UserMaidPercentService {
    public UserMaidPercent getUserMaidPercentByMemberLevelAndMerchantId(Integer memberLevel, String MerchantId);
}
