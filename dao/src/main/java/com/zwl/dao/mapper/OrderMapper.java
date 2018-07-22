package com.zwl.dao.mapper;

import com.zwl.model.po.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {

    int insertSelective(Order record);
    Order selectByPrimaryKey(String id);
    int updateByPrimaryKeySelective(Order record);
    int updateByPrimaryKey(Order record);
    List<Order> getOrderList(Order order);
    Order findOrderByOrderNo(String orderNo);
    @Update("update ss_order set order_status =1 ,  payment_time =#{time_end}, payment_no=#{transaction_id} ,pay_way =1 where order_no=#{out_trade_no}")
    int updateOrder(@Param("out_trade_no") String out_trade_no, @Param("time_end") String time_end, @Param("transaction_id") String transaction_id);
    @Update("update ss_order set wx_sign =#{wxSign} where order_no =#{orderNo}")
    int updateWxSign(@Param("wxSign") String wxSign,@Param("orderNo") String orderNo);
    Order getOrderById(@Param("merchantId") String merchantId, @Param("orderNo") String orderNo);
    List<Order> getOrderListByUserId(String userId);
}