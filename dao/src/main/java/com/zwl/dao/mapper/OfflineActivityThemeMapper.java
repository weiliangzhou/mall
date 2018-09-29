package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityTheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfflineActivityThemeMapper {
    OfflineActivityTheme selectByPrimaryKey(Integer id);
    List<OfflineActivityTheme> getOfflineActivityThemeListByQueryType(@Param("merchantId") String merchantId, @Param("queryType") String queryType);
    OfflineActivityTheme getOfflineActivityThemeDetailByThemeId(@Param("merchantId") String merchantId, @Param("themeId") String themeId);
}