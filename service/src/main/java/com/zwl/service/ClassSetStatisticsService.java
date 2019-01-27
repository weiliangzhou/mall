package com.zwl.service;

import com.zwl.model.po.ClassSetStatistics;

public interface ClassSetStatisticsService {
    /**
     * 新增
     * @return
     */
    int add(ClassSetStatistics classSetStatistics);

    /**
     * 浏览人数+1
     * @return
     */
    int setpAddBrowseCount(Long classSetId);

    /**
     * 根据套课程id查找
     * @param classSetId
     * @return
     */
    ClassSetStatistics getByClassSetId(Long classSetId);
}
