package com.zwl.dao.mapper;

import com.zwl.model.po.OfflineActivityTheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfflineActivityThemeMapper {
    int insertSelective(OfflineActivityTheme record);

    OfflineActivityTheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfflineActivityTheme record);

    List<OfflineActivityTheme> getOfflineActivityThemeListByQueryType(@Param("merchantId") String merchantId, @Param("queryType") String queryType);
}