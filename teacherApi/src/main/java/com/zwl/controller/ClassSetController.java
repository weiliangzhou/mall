package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassInfo;
import com.zwl.model.po.ClassSet;
import com.zwl.model.vo.ClassVo;
import com.zwl.model.vo.PageClassVo;
import com.zwl.service.ClassInfoService;
import com.zwl.service.ClassSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 套课程controller
 */
@RequestMapping("/teacher/classset")
@RestController
public class ClassSetController {
    @Autowired
    private ClassSetService classSetService;
    @Autowired
    private ClassInfoService classInfoService;
    /**
     * 套课程新增
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody ClassSet classSet) {
        Result result = new Result();
        classSet.setAvailable(1);
        int flag=classSetService.add(classSet);
        defineResult(result, flag);
        return result;
    }

    private void defineResult(Result result, int addFlag) {
        if(addFlag==-1){
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        }else if(addFlag==0){
            result.setCode(ResultCodeEnum.FAIL);
        }
    }

    /**
     * 套课程修改
     * @return
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody ClassSet classSet) {
        Result result = new Result();
        int flag=classSetService.modifyByParams(classSet);
        defineResult(result, flag);
        return result;
    }

  /*  *//**
     *
     * @param jsonObject
     * @return
     *//*
    @PostMapping("/getPageAllClass")
    public Result getPageAllClass(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        //title 可空
        String title = jsonObject.getString("title");
        Page page=PageHelper.startPage(pageNum, pageSize);
        List<ClassVo> list=classSetService.getAllClass(merchantId,title);
        PageClassVo pageClassVo=new PageClassVo();
        pageClassVo.setPageNum(pageNum);
        pageClassVo.setTotalPage(page.getTotal());
        pageClassVo.setList(list);
        result.setData(pageClassVo);
        return result;
    }*/
    /**
     * @param jsonObject
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = jsonObject.getLong("id");
        ClassSet classSet=classSetService.getById(id);
        if(classSet==null){
            result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("查无此套课，请确认套课id传入正确");
        }
        result.setData(classSet);
        return result;
    }

    /**
     * 获取所有课程列表（套课下节课程也返回）
     * @param jsonObject
     * @return
     */
    @PostMapping("/getPageClassList")
    public String getPageClassList(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        //title 可空
        String title = jsonObject.getString("title");
        Page page=PageHelper.startPage(pageNum, pageSize);
        List<ClassVo> list=classSetService.getAllClass(merchantId,title);
        for (ClassVo c:list
                ) {
            if(c.getClassType()==1){
                List<ClassInfo> classInfoList = classInfoService.getByClassSetId(c.getId());
                List<ClassVo> children =new ArrayList<>();
                for (ClassInfo classInfo: classInfoList
                        ) {
                    ClassVo classVo = new ClassVo();
                    classVo.setId(classInfo.getId());
                    classVo.setTitle(classInfo.getTitle());
                    classVo.setCreateTime(classInfo.getCreateTime());
                    classVo.setModifyTime(classInfo.getModifyTime());
                    classVo.setCategoryTitle(c.getCategoryTitle());
                    children.add(classVo);
                }
                c.setChildren(children);
            }
        }

        PageClassVo pageClassVo=new PageClassVo();
        pageClassVo.setPageNum(pageNum);
        pageClassVo.setTotalPage(page.getTotal());
        pageClassVo.setList(list);
        result.setData(pageClassVo);
        return JSON.toJSONString(result);
    }
}
