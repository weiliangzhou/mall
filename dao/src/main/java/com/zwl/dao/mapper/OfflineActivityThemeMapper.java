package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityTheme;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OfflineActivityThemeMapper {
    OfflineActivityTheme selectByPrimaryKey(Integer id);

    List<OfflineActivityTheme> getOfflineActivityThemeListByQueryType(@Param("merchantId") String merchantId, @Param("queryType") String queryType, @Param("activityType")Integer activityType);

    OfflineActivityTheme getOfflineActivityThemeDetailByThemeId(@Param("merchantId") String merchantId, @Param("themeId") Integer themeId);

    @Update("update ss_offline_activity_theme set buy_count=buy_count+1 where  id =#{activityThemeId}")
    void updateBuyCountById(@Param("activityThemeId") Integer activityThemeId);
}