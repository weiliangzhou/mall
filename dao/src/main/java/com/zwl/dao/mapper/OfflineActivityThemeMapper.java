package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityTheme;

public interface OfflineActivityThemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OfflineActivityTheme record);

    int insertSelective(OfflineActivityTheme record);

    OfflineActivityTheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfflineActivityTheme record);

    int updateByPrimaryKeyWithBLOBs(OfflineActivityTheme record);

    int updateByPrimaryKey(OfflineActivityTheme record);
}