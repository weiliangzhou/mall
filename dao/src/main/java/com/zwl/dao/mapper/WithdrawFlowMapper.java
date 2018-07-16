package com.zwl.dao.mapper;

import com.zwl.model.po.WithdrawFlow;

public interface WithdrawFlowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WithdrawFlow record);

    int insertSelective(WithdrawFlow record);

    WithdrawFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WithdrawFlow record);

    int updateByPrimaryKey(WithdrawFlow record);
}