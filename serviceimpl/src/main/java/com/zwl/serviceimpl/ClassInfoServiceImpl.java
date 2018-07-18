package com.zwl.serviceimpl;

import com.zwl.dao.mapper.ClassInfoMapper;
import com.zwl.model.po.ClassInfo;
import com.zwl.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@SuppressWarnings("All")
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;


    @Override
    public int add(ClassInfo classInfo) {
        if (CheckTitle(classInfo)) return -1;

        return classInfoMapper.insert(classInfo);
    }

    private boolean CheckTitle(ClassInfo classInfo) {
        //是单节 只查单节（不属于套课）标题重复
        if (!StringUtils.isEmpty(classInfo.getCategoryId()) && StringUtils.isEmpty(classInfo.getClassSetId())){
            ClassInfo query = new ClassInfo();

            query.setTitle(classInfo.getTitle());
            query.setMerchantId(classInfo.getMerchantId());
            List<ClassInfo> list = classInfoMapper.selectListByParams(query);
            if(list!=null && list.size()>0){
                return true;
            }

        }
        //属于套课 查重
        if (StringUtils.isEmpty(classInfo.getCategoryId()) && !StringUtils.isEmpty(classInfo.getClassSetId())){
            ClassInfo query = new ClassInfo();

            query.setTitle(classInfo.getTitle());
            query.setCategoryId(classInfo.getClassSetId());
            List<ClassInfo> list = classInfoMapper.selectListByParams(query);
            if(list!=null && list.size()>0){
                return true;
            }

        }
        return false;
    }

    @Override
    public ClassInfo getById(Long id) {
        return classInfoMapper.selectById(id);
    }

    @Override
    public int modifyByParams(ClassInfo classInfo) {
        if (CheckTitle(classInfo)) return -1;
        return classInfoMapper.updateByParams(classInfo);
    }

    @Override
    public List<ClassInfo> getListByMerchantId(String merchantId) {
        return classInfoMapper.selectListByMerchantId(merchantId);
    }
}
