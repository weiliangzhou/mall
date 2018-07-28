package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.PageResult;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassCategory;
import com.zwl.service.ClassCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程分类controller
 */
@RestController
@RequestMapping("/teacher/classcategory")
public class ClassCategoryController {
    @Autowired
    private ClassCategoryService classCategoryService;
    @PostMapping("/add")
    public Result add(@RequestBody ClassCategory classCategory){
        Result result = new Result();
        int addFlag=classCategoryService.add(classCategory);
        if(addFlag==-1){
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        }else if(addFlag==0){
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }
    @PostMapping("/modify")
    public Result modify(@RequestBody ClassCategory classCategory){
        Result result = new Result();
        int modifyFlag=classCategoryService.modifyByParams(classCategory);
        if( modifyFlag==-1){
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        }else if( modifyFlag==0){
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }
    @PostMapping("/getPageList")
    public String getPageListByMerchantId(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        String merchantId=jsonObject.getString("merchantId");
        Integer pageNum=jsonObject.getInteger("pageNum");
        Integer pageSize=jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum,pageSize);
        ClassCategory query = new ClassCategory();
        query.setMerchantId(merchantId);
        List<ClassCategory> list = classCategoryService.getListByParams(query);
        PageResult p=new PageResult(list);
        result.setData(p);
        return JSON.toJSONString(result);
    }
}
