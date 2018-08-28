package com.zwl.dao.mapper;

import com.zwl.model.po.ClassSet;
import com.zwl.model.vo.ClassSetItemVo;
import com.zwl.model.vo.ClassVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 套课程mapper
 */
public interface ClassSetMapper {

    int insert(ClassSet classSet);

    /**
     * 修改指定id的套课程参数
     * @param classSet
     * @return
     */
    int updateByParams(ClassSet classSet);

    /**
     * 获取merchantId下的所有套课程
     * @param merchantId
     * @return
     */
    List<ClassSet> selectListByMerchantId(String merchantId);

    /**
     * 根据id获取一个套课程详情
     * @param id
     * @return
     */
    ClassSet selectById(Long id);

    /**
     * 根据参数查询
     * @return
     */
    List<ClassSet> selectListByParams(ClassSet classSet);

    /**
     * 更新前查找是否已经title的字段，排除当前id
     * @param title
     * @param id
     */
    @Select("Select count(id) from ss_class_set   where  title=#{ title}  and merchant_id=#{merchantId} and id <> #{id}")
    int selecetCountByTitleUpdate(@Param("title") String title, @Param("id") Long id,@Param("merchantId") String merchantId);

    /**
     * 获取所有的课程列表
     * 包括套课程 和 单独的节课程
     * @param merchantId
     * @return
     */
    List<ClassVo> selectAllClass(@Param("merchantId") String merchantId,@Param("title") String title);
    /**
     * 获取所有的课程列表
     * 包括套课程 和 单独的节课程
     * @param merchantId
     * @return
     */
    List<ClassVo> selectAllClassOrderById(@Param("merchantId") String merchantId);
    @Select("select id ,title from ss_class_set where merchant_id=#{merchantId} and category_id= #{categoryId} and available=1")
    List<ClassSetItemVo> getClassSetItemsList(@Param("categoryId")Integer categoryId, @Param("merchantId")String merchantId);

    /**
     * 搜索
     * @return
     */
    List<ClassVo> search(@Param("merchantId") String merchantId, @Param("title") String title);

    /**
     * 删除套课
     * @param id
     * @return
     */
    @Update("update ss_class_set set available =0 where id=#{id}")
    int deleteClassSet(@Param("id")Long id);
}