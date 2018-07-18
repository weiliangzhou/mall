package com.zwl.dao.mapper;

import com.zwl.model.po.ClassSet;

import java.util.List;

/**
 * 套课程mapper
 */
public interface ClassSetMapper {

    int insert(ClassSet classSet);

    /**
     * 修改指定id的套课程参数
     * @param classSet
     * @return
     */
    int updateByParams(ClassSet classSet);

    /**
     * 获取merchantId下的所有套课程
     * @param merchantId
     * @return
     */
    List<ClassSet> selectListByMerchantId(String merchantId);

    /**
     * 根据id获取一个套课程详情
     * @param id
     * @return
     */
    ClassSet selectById(Long id);

    /**
     * 根据参数查询
     * @return
     */
    List<ClassSet> selectListByParams(ClassSet classSet);
}