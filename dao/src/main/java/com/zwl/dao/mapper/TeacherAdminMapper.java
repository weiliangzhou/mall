package com.zwl.dao.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherAdminMapper {
    @Select("select count(*) from ss_teacher_admin where user_name=#{userName} and password=#{pwd}")
    Integer checkLogin(@Param("userName") String userName, @Param("pwd") String pwd);
}