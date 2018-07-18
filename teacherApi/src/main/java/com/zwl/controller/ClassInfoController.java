package com.zwl.controller;

import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassInfo;
import com.zwl.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 节课程controller
 */
@RequestMapping("/wx/classinfo")
@RestController
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;

    @PostMapping("/add")
    public Result add(@RequestBody ClassInfo classInfo){
        Result result = new Result();
        int addFlag=classInfoService.add(classInfo);
        if(addFlag==-1){
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        }else if(addFlag==0){
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }
    @PostMapping("/modify")
    public Result modify(@RequestBody ClassInfo classInfo){
        Result result = new Result();
        int modifyFlag=classInfoService.modifyByParams(classInfo);
        if(modifyFlag==-1){
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        }else if(modifyFlag==0){
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }

}
