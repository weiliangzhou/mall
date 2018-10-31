package com.zwl.dao.mapper;

import com.zwl.model.po.Gift;

import java.util.List;

public interface GiftMapper {
    int insertSelective(Gift record);
    Gift selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Gift record);
    List getGiftList(Integer queryType, String merchantId);
}