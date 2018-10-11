package com.zwl.dao.mapper;

import com.zwl.model.po.UserWechat;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

public interface UserWechatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWechat record);

    int insertSelective(UserWechat record);

    UserWechat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserWechat record);

    int updateByPrimaryKey(UserWechat record);

    UserWechat getUserWechatByUserId(@Param("userId") String userId);
}