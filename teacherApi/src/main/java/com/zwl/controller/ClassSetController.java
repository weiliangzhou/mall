package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.PageResult;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassSet;
import com.zwl.model.vo.ClassSetItemVo;
import com.zwl.model.vo.ClassVo;
import com.zwl.model.vo.PageClassVo;
import com.zwl.service.ClassSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 套课程controller
 */
@RequestMapping("/teacher/classset")
@RestController
public class ClassSetController {
    @Autowired
    private ClassSetService classSetService;

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

    /**
     * @param jsonObject
     * @return
     */
    @PostMapping("/getPageAllClass")
    public Result getPageAllClass(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page=PageHelper.startPage(pageNum, pageSize);
        List<ClassVo> list=classSetService.getAllClass(merchantId);
        PageClassVo pageClassVo=new PageClassVo();
        pageClassVo.setPageNum(pageNum);
        pageClassVo.setTotalPage(page.getTotal());
        pageClassVo.setList(list);
        result.setData(pageClassVo);
        return result;
    }
    /**
     * @param jsonObject
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = jsonObject.getLong("id");
        ClassSet classSet=classSetService.getById(id);
        result.setData(classSet);
        return result;
    }

    @PostMapping("/getClassSetItemsList")
    public Result getClassSetItemsList(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer categoryId = jsonObject.getInteger("categoryId");
        List<ClassSetItemVo> list = classSetService.getClassSetItemsList(categoryId, merchantId);
        result.setData(list);
        return result;
    }


}
