package com.zwl.serviceimpl;

import com.zwl.dao.mapper.ClassInfoCommentMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.ClassInfoComment;
import com.zwl.service.ClassInfoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassInfoCommentServiceImpl implements ClassInfoCommentService {
    @Autowired
    private ClassInfoCommentMapper classInfoCommentMapper;
    @Override
    public List<ClassInfoComment> getClassInfoCommentList(ClassInfoComment classInfoComment) {
        return classInfoCommentMapper.getClassInfoCommentList(classInfoComment);
    }

    @Override
    public int addClassInfoComment(ClassInfoComment classInfoComment) {
        String comment = classInfoComment.getComment();
        if(10>comment.length() || 100<comment.length()) {
            BSUtil.isTrue(false,"评论字数应在10-100字之间");
        }
        return classInfoCommentMapper.insertSelective(classInfoComment);
    }

    @Override
    public List<ClassInfoComment> getClassInfoCommentListByClassInfoId(ClassInfoComment classInfoComment) {
        return classInfoCommentMapper.getClassInfoCommentListByClassInfoId(classInfoComment);
    }
}
