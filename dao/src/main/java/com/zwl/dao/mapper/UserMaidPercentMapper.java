package com.zwl.dao.mapper;

import com.zwl.model.po.UserMaidPercent;
import org.apache.ibatis.annotations.Param;

public interface UserMaidPercentMapper {
    UserMaidPercent getUserMaidPercentByMemberLevelAndMerchantId(@Param("memberLevel") Integer memberLevel, @Param("merchantId") String merchantId);
}