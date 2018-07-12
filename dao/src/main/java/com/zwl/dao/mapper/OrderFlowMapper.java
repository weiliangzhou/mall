package com.zwl.dao.mapper;

import com.zwl.model.OrderFlow;

public interface OrderFlowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderFlow record);

    int insertSelective(OrderFlow record);

    OrderFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderFlow record);

    int updateByPrimaryKey(OrderFlow record);
}