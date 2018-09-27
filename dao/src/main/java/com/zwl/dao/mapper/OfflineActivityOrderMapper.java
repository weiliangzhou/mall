package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityOrder;

public interface OfflineActivityOrderMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(OfflineActivityOrder record);

    int insertSelective(OfflineActivityOrder record);

    OfflineActivityOrder selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(OfflineActivityOrder record);

    int updateByPrimaryKey(OfflineActivityOrder record);
}