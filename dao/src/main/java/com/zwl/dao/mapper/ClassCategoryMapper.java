package com.zwl.dao.mapper;

import com.zwl.model.po.ClassCategory;

import java.util.List;

public interface ClassCategoryMapper {
    /**
     * 新增课程分类
     * @return
     */
    int insert(ClassCategory classCategory);

    /**
     * 获取商户下的所有课程分类
     * @return
     */
    List<ClassCategory> selectListByMerchantId(String merchantId);

    /**
     * 修改
     * @return
     */
    int updateByParams(ClassCategory classCategory);

    /**
     * 获取商户下的所有课程分类
     * @return
     */
    List<ClassCategory> selectListByparams(ClassCategory classCategory);
}