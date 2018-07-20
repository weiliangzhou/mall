package com.zwl.mapper;

import com.zwl.model.ClassInfoStatistics;

public interface ClassInfoStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassInfoStatistics record);

    int insertSelective(ClassInfoStatistics record);

    ClassInfoStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassInfoStatistics record);

    int updateByPrimaryKey(ClassInfoStatistics record);
}