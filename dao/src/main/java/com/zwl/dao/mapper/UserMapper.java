package com.zwl.dao.mapper;

import com.zwl.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select user_name as userName ,pass_word as passWord  from ss_user where user_name =#{userName}")
    User getUserByUserName(@Param("userName") String userName);
    User getUserByUserId(String userId);
    User getUserById(String userId);
    int updateUserById(User user);
    Integer getMemberLevel(String userId);

    Integer getMaidPercentByUserId(String userId);
}