package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface OfflineActivityOrderMapper {
    int insertSelective(OfflineActivityOrder record);

    OfflineActivityOrder selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(OfflineActivityOrder record);

    OfflineActivityOrder findOrderByActivityCode(String activityCode);

    List<OfflineActivityOrder> getActivityOrderListByUserId(@Param("userId") String userId, @Param("merchantId") String merchantId);

    @Update("update ss_offline_activity_order set order_status=1 ,payment_no =#{payment_no} ,payment_time=#{paymentTime} where order_no =#{out_trade_no} ")
    int updateStatusByOrderNo(@Param("out_trade_no") String out_trade_no, @Param("payment_no") String payment_no, @Param("paymentTime") String paymentTime);

    OfflineActivityOrder getOfflineActivityOrderByActivityDate(@Param("userId") String userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<OfflineActivityOrder> findSLOrderByUserId(@Param("userId") String userId, @Param("merchantId") String merchantId);
}