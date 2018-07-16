package com.zwl.service;

import com.zwl.model.po.ClassCategory;

import java.util.List;

public interface ClassCategoryService {
    /**
     * 新增课程分类
     * @return
     */
    int add(ClassCategory classCategory);

    /**
     * 获取商户下的所有课程分类
     * @return
     */
    List<ClassCategory> getListByMerchantId(String merchantId);

    /**
     * 修改
     * @return
     */
    int modifyByParams(ClassCategory classCategory);
}
