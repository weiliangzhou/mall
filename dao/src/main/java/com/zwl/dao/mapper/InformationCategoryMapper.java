package com.zwl.dao.mapper;

import com.zwl.model.InformationCategory;

public interface InformationCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InformationCategory record);

    int insertSelective(InformationCategory record);

    InformationCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InformationCategory record);

    int updateByPrimaryKey(InformationCategory record);
}