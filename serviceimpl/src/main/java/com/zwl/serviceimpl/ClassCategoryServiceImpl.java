package com.zwl.serviceimpl;

import com.zwl.dao.mapper.ClassCategoryMapper;
import com.zwl.model.po.ClassCategory;
import com.zwl.service.ClassCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClassCategoryServiceImpl implements ClassCategoryService {
    @Autowired
    private ClassCategoryMapper classCategoryMapper;

    @Override
    public int add(ClassCategory classCategory) {
        //先查询是否已经存在同名称的课程分类
        ClassCategory query=new ClassCategory();
        query.setTitle(classCategory.getTitle());
        List<ClassCategory> list = classCategoryMapper.selectListByParams(query);
        if(list!=null && list.size()>0){
            return -1;
        }
        return classCategoryMapper.insert(classCategory);
    }

    @Override
    public List<ClassCategory> getListByParams(ClassCategory classCategory) {
        return classCategoryMapper.selectListByParams(classCategory);
    }


    @Override
    public int modifyByParams(ClassCategory classCategory) {
        if(!StringUtils.isEmpty(classCategory.getTitle())){
            //先查询是否已经存在同名称的课程分类
            ClassCategory query=new ClassCategory();
            query.setTitle(classCategory.getTitle());
            List<ClassCategory> list = classCategoryMapper.selectListByParams(query);
            if(list!=null && list.size()>0){
                return -1;
            }
        }
        return classCategoryMapper.updateByParams(classCategory);
    }

    @Override
    public ClassCategory getById(Long id) {
        return classCategoryMapper.getById(id);
    }
}
