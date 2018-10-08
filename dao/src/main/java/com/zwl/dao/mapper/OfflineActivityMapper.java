package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfflineActivityMapper {

    int insertSelective(OfflineActivity record);

    OfflineActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfflineActivity record);

    OfflineActivity getOneByActivityIdAndCheckTime(Integer id);

    List<OfflineActivity> getOfflineActivityListByThemeId(@Param("merchantId") String merchantId, @Param("themeId") String themeId);
}