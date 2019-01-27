package com.zwl.dao.mapper;

import com.zwl.model.po.BankCard;
import org.apache.ibatis.annotations.Param;

public interface BankCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(BankCard record);

    BankCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankCard record);

    int updateByPrimaryKey(BankCard record);

    BankCard getBankCardByUserId(@Param("userId") String userId);
}