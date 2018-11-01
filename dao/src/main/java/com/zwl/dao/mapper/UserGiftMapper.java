package com.zwl.dao.mapper;

import com.zwl.model.po.UserGift;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGiftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGift record);

    int insertSelective(UserGift record);

    UserGift selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserGift record);

    int updateByPrimaryKey(UserGift record);

    /**
     * 分页查询SQL
     *
     * @param userId     用户
     * @param merchantId 商户号
     * @return 列表
     */
    List<UserGift> findUserGiftListPage(@Param("userId") String userId, @Param("merchantId") String merchantId);
}