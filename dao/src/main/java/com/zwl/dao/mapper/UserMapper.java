package com.zwl.dao.mapper;

import com.zwl.model.po.User;

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
}