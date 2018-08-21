package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Banner;
import com.zwl.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api2Doc(name="banner管理")
@RestController
public class BannerController {

     @Autowired
     private BannerService  bannerService;

     @ApiComment(value = "获取banner列表",seeClass = Banner.class)
     @RequestMapping(name = "获取banner列表", value = "/teacher/banner/getBannerList", method = RequestMethod.POST)
     public Banner getBannerList(@ApiComment("商户号") String merchantId ,
                                 @ApiComment(value = "是否展示",sample = "0不展示 1展示") Integer isShow ,
                                 @ApiComment("banner主题") String theme){
         Banner banner=new Banner();
         return banner;
     }

    @ApiComment("banner添加")
    @RequestMapping(name = "banner添加", value = "/teacher/banner/add", method = RequestMethod.POST)
    public String addBanner(@ApiComment("Banner对象")Banner banner) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }

    @ApiComment("banner编辑")
    @RequestMapping(name = "banner编辑", value = "/teacher/banner/update", method = RequestMethod.POST)
    public String updateBanner(@ApiComment("Banner对象")Banner banner) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }

    @ApiComment("banner删除")
    @RequestMapping(name = "banner删除", value = "/teacher/banner/delete", method = RequestMethod.POST)
    public String deleteBanner(@ApiComment("id") Integer id){
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }
}
