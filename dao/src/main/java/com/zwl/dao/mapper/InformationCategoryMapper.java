package com.zwl.dao.mapper;

import com.zwl.model.po.InformationCategory;

public interface InformationCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InformationCategory record);

    int insertSelective(InformationCategory record);

    InformationCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InformationCategory record);

    int updateByPrimaryKey(InformationCategory record);
}