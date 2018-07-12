package com.zwl.mapper;

import com.zwl.model.UserCertification;

public interface UserCertificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCertification record);

    int insertSelective(UserCertification record);

    UserCertification selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCertification record);

    int updateByPrimaryKey(UserCertification record);
}