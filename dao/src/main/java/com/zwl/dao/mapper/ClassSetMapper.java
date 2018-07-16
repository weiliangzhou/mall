package com.zwl.dao.mapper;

import com.zwl.model.po.ClassSet;

public interface ClassSetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassSet record);

    int insertSelective(ClassSet record);

    ClassSet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassSet record);

    int updateByPrimaryKeyWithBLOBs(ClassSet record);

    int updateByPrimaryKey(ClassSet record);
}