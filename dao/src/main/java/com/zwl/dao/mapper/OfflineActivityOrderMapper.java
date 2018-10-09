package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.po.Order;

import java.util.List;

public interface OfflineActivityOrderMapper {
    int insertSelective(OfflineActivityOrder record);
    OfflineActivityOrder selectByPrimaryKey(String orderNo);
    int updateByPrimaryKeySelective(OfflineActivityOrder record);

    OfflineActivityOrder findOrderByActivityCode(String activityCode);

    List<OfflineActivityOrder> findOrderByUserId(String userId, String merchantId);
}