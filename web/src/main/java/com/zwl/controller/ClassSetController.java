package com.zwl.controller;

import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.ClassSet;
import com.zwl.service.ClassSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套课程controller
 */
@RequestMapping("/wx/classset")
@RestController
public class ClassSetController {
    @Autowired
    private ClassSetService classSetService;

    /**
     *  新增套课程
     * @return
     */
    /*@PostMapping("/add")
    public Result add(@RequestBody ClassSet classSet) {
        Result result = new Result();
       //必须属于某 课程分类

        int addFlag=classSetService.add(classSet);
        if(addFlag==-1){
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        }else if(addFlag==0){
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }

    @PostMapping("/modify")
    public Result modify(@RequestBody ClassSet classSet){
        Result result = new Result();
        int modifyFlag=classSetService.modifyByParams(classSet);
        if( modifyFlag==-1){
            result.setCode(ResultCodeEnum.FAIL);
            result.setMessage("已经存在相同的名称");
        }else if( modifyFlag==0){
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }*/

}
