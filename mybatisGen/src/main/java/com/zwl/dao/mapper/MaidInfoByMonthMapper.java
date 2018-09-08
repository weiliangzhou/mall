package com.zwl.dao.mapper;

import com.zwl.model.po.MaidInfoByMonth;

public interface MaidInfoByMonthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaidInfoByMonth record);

    int insertSelective(MaidInfoByMonth record);

    MaidInfoByMonth selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaidInfoByMonth record);

    int updateByPrimaryKey(MaidInfoByMonth record);
}