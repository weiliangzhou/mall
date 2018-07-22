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
    public Order findOrderByOrderNo(String OrderNo) {
        return orderMapper.findOrderByOrderNo(OrderNo);
    }

    @Override
    public int updateOrder(String out_trade_no, String time_end, String transaction_id) {
        return orderMapper.updateOrder(out_trade_no, time_end, transaction_id);
    }

    @Override
    public Order getOrderById(String merchantId, String orderNo) {
        return orderMapper.getOrderById(merchantId,orderNo);
    }

    @Override
    public List<Order> getOrderListByUserId(String userId) {
        return orderMapper.getOrderListByUserId(userId);
    }
}
