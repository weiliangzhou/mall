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
    Order findOrderByOrderNo(String OrderNo);
    int updateOrder(String out_trade_no, String time_end, String transaction_id);
    Order getOrderById(String merchantId, String orderNo);
    List<Order> getOrderListByUserId(String userId);
}
