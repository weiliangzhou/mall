package com.zwl.dao.mapper;

import com.zwl.model.po.ClassInfoComment;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ClassInfoCommentMapper {
    @Update("update ss_class_info_comment set available =0 where id=#{id}")
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ClassInfoComment record);

    List<ClassInfoComment> getClassInfoCommentList(ClassInfoComment classInfoComment);

    int updateByPrimaryKeySelective(ClassInfoComment record);

    List<ClassInfoComment> getClassInfoCommentListByClassInfoId(ClassInfoComment classInfoComment);
}