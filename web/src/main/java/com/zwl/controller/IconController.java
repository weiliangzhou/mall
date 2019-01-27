package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.baseController.BaseController;
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
public class IconController extends BaseController {
    @Autowired
    private IconService iconService;

    @PostMapping("/getIconList")
    public String getIconList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer portType = jsonObject.getInteger("portType");
        Icon icon = new Icon();
        icon.setMerchantId(merchantId);
        if (null != portType) {
            icon.setPortType(portType);
        } else {
            icon.setPortType(0);
        }
        List<Icon> iconList = iconService.getIconList(icon);
        return setSuccessResult(iconList);
    }
}
