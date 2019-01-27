package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OrderMapper;
import com.zwl.model.po.Order;
import com.zwl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OrderServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/920:52
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getOrderList(Order order) {
        return orderMapper.getOrderList(order);
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order findOrderByOrderNo(String OrderNo) {
        return orderMapper.findOrderByOrderNo(OrderNo);
    }

    @Override
    public void updateOrderSetOverTime() {
        orderMapper.updateOrderSetOverTime();
    }

    @Override
    public int getAlreadyBuyCount(String userId, Long productId) {
        return orderMapper.getAlreadyBuyCount(userId, productId);
    }

    @Override
    public Integer getConsumeAmountByPhoneAndMid(String registerMobile, String mid) {
        return orderMapper.getConsumeAmountByPhoneAndMid(registerMobile, mid);
    }


}
