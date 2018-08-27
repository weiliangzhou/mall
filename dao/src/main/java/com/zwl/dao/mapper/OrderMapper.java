package com.zwl.dao.mapper;

import com.zwl.model.po.Order;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    List<Order> getOrderList(Order order);

    Order findOrderByOrderNo(String orderNo);

    @Update("update ss_order set order_status = -1 where order_status= 0 ")
    void updateOrderSetOverTime();
}