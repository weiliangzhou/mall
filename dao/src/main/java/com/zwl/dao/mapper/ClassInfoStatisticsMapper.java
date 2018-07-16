package com.zwl.dao.mapper;

import com.zwl.model.po.ClassInfoStatistics;

public interface ClassInfoStatisticsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClassInfoStatistics record);

    int insertSelective(ClassInfoStatistics record);

    ClassInfoStatistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClassInfoStatistics record);

    int updateByPrimaryKey(ClassInfoStatistics record);
}