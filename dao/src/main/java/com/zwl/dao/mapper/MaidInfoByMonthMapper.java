package com.zwl.dao.mapper;

import com.zwl.model.po.MaidInfoByMonth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MaidInfoByMonthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaidInfoByMonth record);

    int insertSelective(MaidInfoByMonth record);

    MaidInfoByMonth selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaidInfoByMonth record);

    int updateByPrimaryKey(MaidInfoByMonth record);

    int getExistCountByUserIdAndRecordTime(@Param("userId") String xzUserId, @Param("recordTime") String recordTime, @Param("maidType") Integer maidType);
    @Select("SELECT sum(maid_money) from ss_maid_info_by_month where user_id=#{userId} and available=1")
    Integer getTotalAmountByMonthByUserId(String userId);
}