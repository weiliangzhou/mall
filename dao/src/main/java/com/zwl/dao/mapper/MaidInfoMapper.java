package com.zwl.dao.mapper;

import com.zwl.model.MaidInfo;

import java.util.List;

public interface MaidInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaidInfo record);

    int insertSelective(MaidInfo record);

    MaidInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaidInfo record);

    int updateByPrimaryKey(MaidInfo record);

    List<MaidInfo> getMaidInfoList(String userId);
}