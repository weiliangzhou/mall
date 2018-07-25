package com.zwl.serviceimpl;

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
    public List<ClassVo> getAllClass(String merchantId,String title) {
        return classSetMapper.selectAllClass(merchantId,title);
    }

    @Override
    public List<ClassSetItemVo> getClassSetItemsList(Integer categoryId, String merchantId) {
        return classSetMapper.getClassSetItemsList( categoryId,  merchantId);
    }

    @Override
    public List<ClassVo> search(String merchantId, String title) {
        return classSetMapper.search(merchantId,title);
    }
}
