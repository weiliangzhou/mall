package com.zwl.dao.mapper;

import com.zwl.model.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {


    int deleteByPrimaryKey(Long id);

    /**
     * 新增用户
     * @return userId
     */
    int insert(User user);

    /**
     *
     * @param user
     * @return
     */
    User selectOneByParams(User user);

    User getUserByUserId(String userId);
//    User getUserById(String userId);
    int updateUserById(User user);
    Integer getMemberLevel(String userId);

    Integer getMaidPercentByUserId(String userId);

    /**
     * 根据用户参数获取list
     * @param user
     * @return
     */
    List<User> selectListByParams(User user);

    /**
     * 根据会员等级获取用户列表
     * @param merchantId
     * @param lowLevel:最低会员等级
     * @param topLevel:最高会员等级
     * @return
     */
    List<User> selectListByLevel(@Param("merchantId")String merchantId, @Param("lowLevel") Integer lowLevel, @Param("topLevel") Integer topLevel);
 }