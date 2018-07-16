package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Information;
import com.zwl.service.InformationService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: 资讯列表
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1215:04
 */
@RestController
public class InformationController {
    @Autowired
    private InformationService informationService;

    @PostMapping("/teacher/addInformation")
    public String addInformation(@Validated(Update.class) @RequestBody Information information) {
        Result result = new Result();
        int count = informationService.addInformation(information);
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/teacher/updateInformation")
    public String updateInformation(@Validated(Update.class) @RequestBody Information information) {
        Result result = new Result();
        int count = informationService.updateInformation(information);
        return JSONObject.toJSONString(result);
    }

}
