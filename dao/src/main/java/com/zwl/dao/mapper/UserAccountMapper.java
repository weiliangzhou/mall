package com.zwl.dao.mapper;

import com.zwl.model.UserAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAccount record);

    int insertSelective(UserAccount record);

    UserAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAccount record);

    int updateByPrimaryKey(UserAccount record);

    Integer getBalanceByUserId(String userId);

    @Update("update ss_user_account set balance= #{currentAmount} where user_id=#{userId}")
    int updateBanlanceByUserId(@Param("userId") String userId, @Param("currentAmount") double currentAmount);
}