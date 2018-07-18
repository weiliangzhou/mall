package com.zwl.dao.mapper;

import com.zwl.model.po.ClassInfo;

import java.util.List;

/**
 * 节课程mapper
 */
public interface ClassInfoMapper {
    /**
     * 新增
     */
    int insert(ClassInfo classInfo);

    /**
     * 根据id获取节课程详情
     * @param id
     * @return
     */
    ClassInfo selectById(Long id);

    /**
     * 更新指定id的参数
     * @param classInfo
     * @return
     */
    int updateByParams(ClassInfo classInfo);

    /**
     * 获取merchantId下的所有节课程
     * @param merchantId
     * @return
     */
    List<ClassInfo> selectListByMerchantId(String merchantId);
    /**
     * 根据参数查找节课程
     * @return
     */
    List<ClassInfo> selectListByParams(ClassInfo classInfo);

}