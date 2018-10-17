package com.zwl.dao.mapper;

import com.zwl.model.po.UserMaidPercent;

public interface UserMaidPercentMapper {
    UserMaidPercent getUserMaidPercentByMemberLevelAndMerchantId(Integer memberLevel, String merchantId);
}