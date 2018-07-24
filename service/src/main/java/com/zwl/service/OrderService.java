package com.zwl.service;

import com.zwl.model.po.Order;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OrderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/920:51
 */
public interface OrderService {
    List<Order> getOrderList(Order order);
    int updateOrder(Order order);
    Order findOrderByOrderNo(String OrderNo);
}
