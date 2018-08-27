package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Icon;
import com.zwl.service.IconService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teacher/icon")
public class IconController {
    @Autowired
    private IconService iconService;

    @PostMapping("/getIconList")
    public String getIconList(@RequestBody JSONObject jsonObject){
        String merchantId=jsonObject.getString("merchantId");
        Icon icon = new Icon();
        icon.setMerchantId(merchantId);
        Result result = new Result();
        List<Icon> iconList = iconService.getIconList(icon);
        result.setData(iconList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/add")
    public String addIcon(@Validated(Update.class) @RequestBody Icon icon ) {
        Result result = new Result();
        int count = iconService.addIcon(icon);
        if(1 != count)BSUtil.isTrue(false,"新增失败");
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/update")
    public String updateIcon(@Validated(Update.class) @RequestBody Icon icon) {
        Result result = new Result();
        int count = iconService.updateIcon(icon);
        if(1 != count)BSUtil.isTrue(false,"修改失败");
        return JSONObject.toJSONString(result);
    }

    @PostMapping("/delete")
    public String deleteIcon(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Integer id = jsonObject.getInteger("id");
        int count = iconService.deleteIcon(id);
        if(1 != count)BSUtil.isTrue(false,"删除失败");
        return JSONObject.toJSONString(result);
    }
}
