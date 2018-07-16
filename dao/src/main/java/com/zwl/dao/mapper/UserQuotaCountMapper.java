package com.zwl.dao.mapper;

import com.zwl.model.po.UserQuotaCount;

public interface UserQuotaCountMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserQuotaCount record);

    int insertSelective(UserQuotaCount record);

    UserQuotaCount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserQuotaCount record);

    int updateByPrimaryKey(UserQuotaCount record);
}