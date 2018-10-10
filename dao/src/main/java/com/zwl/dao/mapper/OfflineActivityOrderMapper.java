package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfflineActivityOrderMapper {
    int insertSelective(OfflineActivityOrder record);
    OfflineActivityOrder selectByPrimaryKey(String orderNo);
    int updateByPrimaryKeySelective(OfflineActivityOrder record);

    OfflineActivityOrder findOrderByActivityCode(String activityCode);

    List<OfflineActivityOrder> findOrderByUserId(@Param("userId") String userId, @Param("merchantId") String merchantId);
}