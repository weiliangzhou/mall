package com.zwl.dao.mapper;

import com.zwl.model.po.ClassSetShareRecord;

public interface ClassSetShareRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassSetShareRecord record);

    int insertSelective(ClassSetShareRecord record);

    ClassSetShareRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassSetShareRecord record);

    int updateByPrimaryKey(ClassSetShareRecord record);
}