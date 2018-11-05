package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.baseController.BaseController;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.ClassInfo;
import com.zwl.model.po.ClassInfoComment;
import com.zwl.model.vo.ClassVo;
import com.zwl.service.ClassInfoCommentService;
import com.zwl.service.ClassInfoService;
import com.zwl.service.ClassSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wx/classInfoComment")
public class ClassInfoCommentController extends BaseController {
    @Autowired
    private ClassInfoCommentService classInfoCommentService;
    @Autowired
    private ClassSetService classSetService;
    @Autowired
    private ClassInfoService classInfoService;

    @PostMapping("/getClassInfoCommentList")
    public String getClassInfoCommentList(@RequestBody JSONObject jsonObject) {
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String merchantId = jsonObject.getString("merchantId");
        Long classInfoId = jsonObject.getLong("classInfoId");
        PageHelper.startPage(pageNum, pageSize);
        ClassInfoComment classInfoComment = new ClassInfoComment();
        classInfoComment.setMerchantId(merchantId);
        classInfoComment.setClassInfoId(classInfoId);
        List<ClassInfoComment> classInfoCommentList = classInfoCommentService.getClassInfoCommentList(classInfoComment);
        return setSuccessResult(classInfoCommentList);
    }

    @PostMapping("/add")
    public String addClassInfoComment(@RequestBody ClassInfoComment classInfoComment) {
        int count = classInfoCommentService.addClassInfoComment(classInfoComment);
        if (1 != count) BSUtil.isTrue(false, "新增失败");
        return setSuccessResult();
    }

    @PostMapping("/getClassInfoList")
    public String getClassInfoList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        List<ClassVo> classVoList = classSetService.getAllClassOrderById(merchantId, 1);
        for (ClassVo c : classVoList) {
            if (c.getClassType() == 1) {
                List<ClassInfo> classInfoList = classInfoService.getByClassSetId(c.getId());
                List<ClassVo> children = new ArrayList<>();
                for (ClassInfo classInfo : classInfoList) {
                    ClassVo classVo = new ClassVo();
                    classVo.setId(classInfo.getId());
                    classVo.setTitle(classInfo.getTitle());
                    children.add(classVo);
                }
                c.setChildren(children);
            }
        }
        return setSuccessResult(classVoList);
    }
}
