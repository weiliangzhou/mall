package com.zwl.mapper;

import com.zwl.model.OperateUserRecord;

public interface OperateUserRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OperateUserRecord record);

    int insertSelective(OperateUserRecord record);

    OperateUserRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperateUserRecord record);

    int updateByPrimaryKey(OperateUserRecord record);
}