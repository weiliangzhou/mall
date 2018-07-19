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
     * 修改
     * @return
     */
    int updateByParams(ClassCategory classCategory);

    /**
     * 获取商户下的所有课程分类
     * @return
     */
    List<ClassCategory> selectListByParams(ClassCategory classCategory);
    /**
     * 根据id获取
     * @param id
     * @return
     */
    ClassCategory getById(Long id);
}