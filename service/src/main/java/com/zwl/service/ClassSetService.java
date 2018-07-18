package com.zwl.service;

import com.zwl.model.po.ClassSet;
import com.zwl.model.vo.ClassVo;

import java.util.List;

public interface ClassSetService {
    /**
     * 新增套课
     * @param classSet
     * @return
     */
    int add(ClassSet classSet);
    /**
     * 修改指定id的套课程参数
     * @param classSet
     * @return
     */
    int modifyByParams(ClassSet classSet);

    /**
     * 获取merchantId下的所有套课程
     * @param merchantId
     * @return
     */
    List<ClassSet> getListByMerchantId(String merchantId);

    /**
     * 根据id获取一个套课程详情
     * @param id
     * @return
     */
    ClassSet getById(Long id);

    /**
     * 获取所有的课程列表
     * 包括套课程 和 单独的节课程
     * @param merchantId
     * @return
     */
    List<ClassVo> getAllClass(String merchantId);
}
