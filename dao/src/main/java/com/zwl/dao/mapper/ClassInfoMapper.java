package com.zwl.dao.mapper;

import com.zwl.model.po.ClassInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    /**
     * 更新前查找单节课程中是否已经title的字段，排除当前id
     * @param title
     * @param id
     */
    @Select("Select count(id) from ss_class_info   where  title=#{ title} and category_id=#{categoryId} and " +
            "merchant_id=#{merchantId} and id <> #{id}")
    int selecetCountByTitleUpdate(@Param("title") String title, @Param("id") Long id,@Param("categoryId") Long categoryId
            ,@Param("merchantId") String merchantId);
    /**
     * 更新前查找套课中 节课程中是否已经title的字段，排除当前id
     * @param title
     * @param id
     */
    @Select("Select count(id) from ss_class_info   where  title=#{ title} and class_set_id=#{classSetId} and " +
            "merchant_id=#{merchantId} and id <> #{id}")
    int selecetCountBySetTitle(@Param("title") String title, @Param("id") Long id,@Param("classSetId") Long classSetId
            ,@Param("merchantId") String merchantId);

}