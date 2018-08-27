package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Icon;
import com.zwl.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/icon")
public class IconController {
    @Autowired
    private IconService iconService;
    @PostMapping("/getIconList")
    public String getIconList(@RequestBody JSONObject jsonObject){
        String merchantId=jsonObject.getString("merchantId");
        Result result = new Result();
        Icon icon = new Icon();
        icon.setMerchantId(merchantId);
        List<Icon> iconList = iconService.getIconList(icon);
        result.setData(iconList);
        return JSON.toJSONString(result);
    }
}
