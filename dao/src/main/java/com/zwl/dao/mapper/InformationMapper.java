package com.zwl.dao.mapper;

import com.zwl.model.po.Information;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface InformationMapper {
    int insertSelective(Information record);
    int updateByPrimaryKeySelective(Information record);
    List<Information> getInformationList(Information information);
    @Update("update ss_information set available =0 where id=#{id}")
    int deleteInformation(@Param("id") Integer id);
}