package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityOperator;

public interface OfflineActivityOperatorMapper {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(OfflineActivityOperator record);
//
//    int insertSelective(OfflineActivityOperator record);
//
//    OfflineActivityOperator selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(OfflineActivityOperator record);
//
//    int updateByPrimaryKey(OfflineActivityOperator record);

    OfflineActivityOperator selectByOperatorAndPassword(OfflineActivityOperator offlineActivityOperator);
}