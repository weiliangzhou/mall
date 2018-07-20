package com.zwl.mapper;

import com.zwl.model.ClassInfoListenRecord;

public interface ClassInfoListenRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassInfoListenRecord record);

    int insertSelective(ClassInfoListenRecord record);

    ClassInfoListenRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassInfoListenRecord record);

    int updateByPrimaryKey(ClassInfoListenRecord record);
}