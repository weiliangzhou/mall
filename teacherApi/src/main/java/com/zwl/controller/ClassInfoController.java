package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.ClassInfo;
import com.zwl.model.po.ClassInfoStatistics;
import com.zwl.model.vo.ClassVo;
import com.zwl.model.vo.PageClassInfoVo;
import com.zwl.service.ClassCategoryService;
import com.zwl.service.ClassInfoService;
import com.zwl.util.CheckUtil;
import com.zwl.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 节课程controller
 */
@RequestMapping("/teacher/classinfo")
@RestController
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private ClassCategoryService classCategoryService;

    @PostMapping("/add")
    public Result add(@RequestBody ParamClassInfoVo paramClassInfoVo) {
        Integer minute = paramClassInfoVo.getMinute();
        Integer second = paramClassInfoVo.getSecond();
        Integer playtime = minute * 60 + second;
        paramClassInfoVo.setPlayTime(playtime);
        Result result = new Result();
        paramClassInfoVo.setAvailable(1);
        int addFlag = classInfoService.add(paramClassInfoVo);
        if (addFlag == -1)
            BSUtil.isTrue(false, "已经存在相同的名称");
        else if (addFlag == 0) {
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }

    @PostMapping("/modify")
    public Result modify(@RequestBody ParamClassInfoVo paramClassInfoVo) {
        Integer minute = paramClassInfoVo.getMinute();
        Integer second = paramClassInfoVo.getSecond();
        Integer playtime = minute * 60 + second;
        paramClassInfoVo.setPlayTime(playtime);
        Result result = new Result();
        int modifyFlag = classInfoService.modifyByParams(paramClassInfoVo);
        if (modifyFlag == -1) {
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        } else if (modifyFlag == 0) {
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }

    /**
     * 根据ClassSetId获取所属的节课程列表
     *
     * @return
     */
    @PostMapping("/getPageByClassSetId")
    public Result getPageByClassSetId(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long classSetId = jsonObject.getLong("classSetId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<ClassInfo> classInfoList = classInfoService.getByClassSetId(classSetId);
        List<ClassVo> listVo=new ArrayList<>();
        if(CheckUtil.isNotEmpty(classInfoList)){
            for (ClassInfo c:classInfoList
                    ) {
                ClassVo classVo = new ClassVo();
                classVo.setId(c.getId());
                classVo.setLogoUrl(c.getLogoUrl());
                classVo.setTitle(c.getTitle());
                classVo.setContentText(c.getContentText());
                classVo.setContent(c.getContent());
                classVo.setModifyTime(c.getModifyTime());
                classVo.setAudioUrl(c.getAudioUrl());
                classVo.setCategoryId(c.getCategoryId());
                classVo.setClassSetId(c.getClassSetId());
                if(null != c.getPlayTime()) {
                    Integer playTime = c.getPlayTime();
                    String playTimeDesc = playTime / 60 + "分" + playTime % 60 + "秒";
                    classVo.setPlayTimeDesc(playTimeDesc);
                }
                classVo.setStyleDesc(c.getStyleDesc());
                listVo.add(classVo);
            }
        }
        PageClassInfoVo pageVo = new PageClassInfoVo();
        pageVo.setPageNum(pageNum);
        pageVo.setTotalPage(page.getTotal());
        pageVo.setList(listVo);
        result.setData(pageVo);
        return result;
    }

    /**
     * 根据Id获取节课程
     *
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = jsonObject.getLong("id");
        ClassInfo classInfo = classInfoService.getById(id);
        result.setData(classInfo);
        return result;
    }

}
