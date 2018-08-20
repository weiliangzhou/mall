package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Banner;
import com.zwl.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/getBannerList")
    public String  getBannerList(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Banner banner=new Banner();
        banner.setMerchantId(merchantId);
        List<Banner> bannerList =bannerService.getWxBannerList(banner);
        result.setData(bannerList);
        return JSON.toJSONString(result);

    }



}
