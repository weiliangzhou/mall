package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.po.Order;

public interface OfflineActivityOrderMapper {
    int insertSelective(OfflineActivityOrder record);
    OfflineActivityOrder selectByPrimaryKey(String orderNo);
    int updateByPrimaryKeySelective(OfflineActivityOrder record);
}