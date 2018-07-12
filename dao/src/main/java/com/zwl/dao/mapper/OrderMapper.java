package com.zwl.dao.mapper;

import com.zwl.model.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> getOrderList(String merchantId);

    Order findOrderByOrderNo(String orderNo);

    @Update("update ss_order set order_status =1 ,  payment_time =#{time_end}, payment_no=#{transaction_id} where order_no=#{out_trade_no}")
    int updateOrder(@Param("out_trade_no") String out_trade_no, @Param("time_end") String time_end, @Param("transaction_id") String transaction_id);
    Order getOrderById(@Param("merchantId") String merchantId, @Param("orderNo") String orderNo);
}