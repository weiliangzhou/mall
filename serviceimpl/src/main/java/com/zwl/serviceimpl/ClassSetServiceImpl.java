package com.zwl.serviceimpl;

import com.zwl.dao.mapper.ClassInfoMapper;
import com.zwl.dao.mapper.ClassSetMapper;
import com.zwl.model.po.ClassInfo;
import com.zwl.model.po.ClassSet;
import com.zwl.model.vo.ClassSetItemVo;
import com.zwl.model.vo.ClassVo;
import com.zwl.service.ClassSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@SuppressWarnings("All")
@Service
public class ClassSetServiceImpl implements ClassSetService {
    @Autowired
    private ClassSetMapper classSetMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Override
    public int add(ClassSet classSet) {
        //先查询是否同名
        ClassSet query=new ClassSet();
        query.setTitle(classSet.getTitle());
        List<ClassSet> list=classSetMapper.selectListByParams(query);
        if(list!=null && list.size()>0){
            return -1;
        }
        return classSetMapper.insert(classSet);
    }

    @Override
    public int modifyByParams(ClassSet classSet) {
        //先查询是否同名
        /*ClassSet query=new ClassSet();
        query.setTitle(classSet.getTitle());
        List<ClassSet> list=classSetMapper.selectListByParams(query);*/
        int count=classSetMapper.selecetCountByTitleUpdate(classSet.getTitle(),classSet.getId(),classSet.getMerchantId());
        if(count>0){
            return -1;
        }
        return classSetMapper.updateByParams(classSet);
    }

    @Override
    public List<ClassSet> getListByMerchantId(String merchantId) {
        return classSetMapper.selectListByMerchantId(merchantId);
    }

    @Override
    public ClassSet getById(Long id) {
        return classSetMapper.selectById(id);
    }

    @Override
    public List<ClassVo> getAllClass(ClassSet classSet) {
        return classSetMapper.selectAllClass(classSet);
    }

    @Override
    public List<ClassVo> getAllClassOrderById(String merchantId,Integer queryType) {
        return classSetMapper.selectAllClassOrderById(merchantId,queryType);
    }

    @Override
    public List<ClassSetItemVo> getClassSetItemsList(Integer categoryId, String merchantId) {
        return classSetMapper.getClassSetItemsList( categoryId,  merchantId);
    }

    @Override
    public List<ClassVo> search(String merchantId, String title) {
        return classSetMapper.search(merchantId,title);
    }

    @Override
    public int deleteClassSet(Long id) {
        int count = classSetMapper.deleteClassSet(id);
        //查询套课下所有未删除节课
        List<ClassInfo> classInfoList = classInfoMapper.selectByClassSetId(id);
        //删除该套课下未删除节课
        for(ClassInfo classInfo:classInfoList){
            classInfoMapper.deleteClassInfo(classInfo.getId());
        }
        return count;
    }
}
