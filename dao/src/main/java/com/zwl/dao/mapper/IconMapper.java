package com.zwl.dao.mapper;

import com.zwl.model.po.Icon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IconMapper {
    @Update("update ss_icon set available =0 where id=#{id}")
    int deleteByPrimaryKey(@Param("id")Integer id);

    int insertSelective(Icon record);

    List<Icon> getIconList(Icon icon);

    int updateByPrimaryKeySelective(Icon record);
}