package com.zwl.serviceimpl;

import com.zwl.dao.mapper.ClassCategoryMapper;
import com.zwl.model.po.ClassCategory;
import com.zwl.service.ClassCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassCategoryServiceImpl implements ClassCategoryService {
    @Autowired
    private ClassCategoryMapper classCategoryMapper;

    @Override
    public int add(ClassCategory classCategory) {
        //先查询是否已经存在同名称的课程分类


        return classCategoryMapper.insert(classCategory);
    }

    @Override
    public List<ClassCategory> getListByMerchantId(String merchantId) {
        return classCategoryMapper.selectListByMerchantId(merchantId);
    }

    @Override
    public int modifyByParams(ClassCategory classCategory) {
        return classCategoryMapper.updateByParams(classCategory);
    }
}
