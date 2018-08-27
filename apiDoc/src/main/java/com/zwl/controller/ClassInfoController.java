package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassInfo;
import com.zwl.model.po.ClassSet;
import com.zwl.model.vo.PageClassInfoVo;
import com.zwl.service.ClassCategoryService;
import com.zwl.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节课程controller
 */

@Api2Doc(name = "节课程管理")
@ApiComment(seeClass = ClassInfo.class)
@RestController
public class ClassInfoController {

    @ApiComment("新增节课程")
    @RequestMapping(name = "新增节课程",
            value = "/teacher/classinfo/add", method = RequestMethod.POST)
    public Result add(@ApiComment("title") String title,  @ApiComment("categoryId") Long categoryId,  @ApiComment("classSetId") Long classSetId
            , @ApiComment("merchantId") String merchantId,   @ApiComment("audioUrl") String audioUrl, @ApiComment("logoUrl") String logoUrl,
                      @ApiComment("content") String content, @ApiComment("节课类型 0音频 1视频") Integer style, @ApiComment("是否推荐 0不推荐 1推荐") Integer isRecommend,
                      @ApiComment("时长分") Integer minute, @ApiComment("时长秒") Integer second){
        Result result = new Result();

        return result;
    }

    @ApiComment("修改节课程")
    @RequestMapping(name = "修改节课程",
            value = "/teacher/classinfo/modify", method = RequestMethod.POST)
    public Result modify(@ApiComment("id") Long id,@ApiComment("title") String title,  @ApiComment("categoryId") Long categoryId,  @ApiComment("classSetId") Long classSetId
            ,  @ApiComment("audioUrl") String audioUrl, @ApiComment("logoUrl") String logoUrl, @ApiComment("content") String content, @ApiComment("节课类型 0音频 1视频") Integer style,
                         @ApiComment("是否推荐 0不推荐 1推荐") Integer isRecommend, @ApiComment("时长分") Integer minute, @ApiComment("时长秒") Integer second){
        Result result = new Result();

        return result;
    }


    @ApiComment("获取套课程下的节课程列表")
    @RequestMapping(name = "获取套课程下的节课程列表",
            value = "/teacher/classinfo/getPageByClassSetId", method = RequestMethod.POST)
    public PageClassInfoVo getPageByClassSetId(@ApiComment("classSetId") Long classSetId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize){
        Result result = new Result();
        PageClassInfoVo pageVo=new PageClassInfoVo();

        return pageVo;
    }

    @ApiComment("根据Id获取节课程")
    @RequestMapping(name = "根据Id获取节课程",
            value = "/teacher/classinfo/getById", method = RequestMethod.POST)
    public ClassInfo getById(@ApiComment("id") Long id){

        ClassInfo classInfo=new ClassInfo();
        return classInfo;
    }

}
