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

    /**
     * 作废超时订单
     */
    void updateOrderSetOverTime();

    /**
     * 获取已支付的订单数
     *
     * @param userId
     * @param productId
     */
    int getAlreadyBuyCount(String userId, Long productId);

    /**
     * 根据手机号查询消费金额
     *
     * @param registerMobile
     * @param mid
     * @return
     */
    Integer getConsumeAmountByPhoneAndMid(String registerMobile, String mid);
}
