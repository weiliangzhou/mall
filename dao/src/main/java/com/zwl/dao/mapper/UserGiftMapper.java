package com.zwl.dao.mapper;

import com.zwl.model.po.UserGift;

public interface UserGiftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGift record);

    int insertSelective(UserGift record);

    UserGift selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserGift record);

    int updateByPrimaryKey(UserGift record);
}