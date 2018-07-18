package com.zwl.mapper;

import com.zwl.model.ClassSet;

public interface ClassSetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassSet record);

    int insertSelective(ClassSet record);

    ClassSet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassSet record);

    int updateByPrimaryKeyWithBLOBs(ClassSet record);

    int updateByPrimaryKey(ClassSet record);
}