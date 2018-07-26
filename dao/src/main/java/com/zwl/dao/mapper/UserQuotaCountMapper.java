package com.zwl.dao.mapper;

import com.zwl.model.po.UserQuotaCount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserQuotaCountMapper {
    @Select("select count from ss_user_quota_count where id=#{userId} and available=1 ")
    Integer getCountByUserId(@Param("userId") String userId);

    @Update("update ss_user_quota_count set  count =count-1 where type=1 and count-1>=0 and id=#{userId}")
    int updateCountByUserId(@Param("userId") String userId);

    int saveOrUpdate(@Param("userId") String userId, @Param("i") int i);

    UserQuotaCount getByUserId(String userId);
}