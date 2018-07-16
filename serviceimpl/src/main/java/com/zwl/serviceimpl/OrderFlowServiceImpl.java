package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OrderFlowMapper;
import com.zwl.model.po.OrderFlow;
import com.zwl.service.OrderFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二师兄超级帅
 * @Title: OrderFlowServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1314:00
 */
@Service
public class OrderFlowServiceImpl implements OrderFlowService {
    @Autowired
    private OrderFlowMapper orderFlowMapper;

    @Override
    public void save(OrderFlow orderFlow) {
        orderFlowMapper.insertSelective(orderFlow);
    }
}
