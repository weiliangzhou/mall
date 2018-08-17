package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.Banner;
import com.zwl.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api2Doc(name="横幅广告图")
@ApiComment(seeClass = Banner.class)
@RestController
public class BannerController {

     @Autowired
     private BannerService  bannerService;

     @ApiComment("横幅广告图查询")
     @RequestMapping(name = "横幅广告图查询", value = "/wx/banner/getBannerList", method = RequestMethod.POST)
    public List<Banner> getBannerList(@RequestBody JSONObject jsonObject){
         List<Banner> banners = bannerService.getBannerList();
         return banners;
     }
}
