package com.zwl.dao.mapper;

import com.zwl.model.po.Gift;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GiftMapper {
    int insertSelective(Gift record);
    Gift selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Gift record);
    List getGiftList(@Param("queryType") Integer queryType,@Param("merchantId") String merchantId);
    Gift getGiftDetailById(Long giftId);
}