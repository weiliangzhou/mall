package com.zwl.dao.mapper;

import com.zwl.model.po.Order;

import java.util.List;

public interface OrderMapper {

    int insertSelective(Order record);
    int updateByPrimaryKeySelective(Order record);
    List<Order> getOrderList(Order order);
    Order findOrderByOrderNo(String orderNo);
}