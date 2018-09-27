package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivity;

public interface OfflineActivityMapper {

    int insertSelective(OfflineActivity record);

    OfflineActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfflineActivity record);
    OfflineActivity getOneByActivityIdAndCheckTime(Integer activityId);
}