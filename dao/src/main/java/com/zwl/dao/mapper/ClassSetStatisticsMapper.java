package com.zwl.dao.mapper;

import com.zwl.model.po.ClassSetStatistics;

public interface ClassSetStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassSetStatistics record);

    int insertSelective(ClassSetStatistics record);

    ClassSetStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassSetStatistics record);

    int updateByPrimaryKey(ClassSetStatistics record);
}