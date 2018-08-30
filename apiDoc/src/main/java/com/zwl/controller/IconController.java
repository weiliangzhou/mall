package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Icon;
import com.zwl.model.po.Video;
import com.zwl.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api2Doc(name = "图标管理")
@RestController
public class IconController {
    @Autowired
    private IconService iconService;

    @ApiComment(value = "获取图标列表", seeClass =Icon.class)
    @RequestMapping(name = "获取图标列表", value = "/teacher/icon/getIconList", method = RequestMethod.POST)
    public Icon getIconList(@ApiComment("商户号") String merchantId){
        Icon icon = new Icon();
        return icon;
    }

    @ApiComment("图标添加")
    @RequestMapping(name = "图标添加", value = "/teacher/icon/add", method = RequestMethod.POST)
    public String addIcon(@ApiComment("图标名称")String title,
                           @ApiComment("图片地址")String pictureUrl,
                           @ApiComment("跳转地址")String hrefUrl,
                          @ApiComment("跳转类型 0不跳转 1应用内跳转 2 应用外跳转")Integer hrefType,
                           @ApiComment("商户号")String merchantId) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }

    @ApiComment("图标编辑")
    @RequestMapping(name = "图标编辑", value = "/teacher/icon/update", method = RequestMethod.POST)
    public String updateIcon(@ApiComment("id")Integer id,
                              @ApiComment("图标名称")String title,
                              @ApiComment("图片地址")String pictureUrl,
                              @ApiComment("跳转地址")String hrefUrl,
                             @ApiComment("跳转类型 0不跳转 1应用内跳转 2 应用外跳转")Integer hrefType,
                              @ApiComment("商户号")String merchantId) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }

    @ApiComment("图标删除")
    @RequestMapping(name = "图标删除", value = "/teacher/icon/delete", method = RequestMethod.POST)
    public String deleteIcon(@ApiComment("id") Integer id){
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }
}
