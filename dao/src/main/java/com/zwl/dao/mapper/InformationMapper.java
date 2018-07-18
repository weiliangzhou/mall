package com.zwl.dao.mapper;

import com.zwl.model.po.Information;

import java.util.List;

public interface InformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Information record);

    int insertSelective(Information record);

    Information selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Information record);

    int updateByPrimaryKeyWithBLOBs(Information record);

    int updateByPrimaryKey(Information record);

    List<Information> getInformationList(Information information);
}