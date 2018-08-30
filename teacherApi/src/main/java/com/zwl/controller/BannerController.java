package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Banner;
import com.zwl.model.vo.BannerVo;
import com.zwl.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/teacher/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 横幅广告图查询分页
     * @param
     * @param jsonObject
     * @return
     */
    @PostMapping("/getBannerList")
    public String getBannerList(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        String merchantId=jsonObject.getString("merchantId");
        Integer isShow = jsonObject.getInteger("isShow");
        String theme = jsonObject.getString("theme");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page = PageHelper.startPage(pageNum, pageSize);
        Banner banner=new Banner();
        banner.setMerchantId(merchantId);
        banner.setIsShow(isShow);
        banner.setTheme(theme);
        List<Banner> bannerList =bannerService.getBannerList(banner);
        BannerVo bannerVo = new BannerVo();
        bannerVo.setPageNum(pageNum);
        bannerVo.setTotalPage(page.getTotal());
        bannerVo.setBannerList(bannerList);
        result.setData(bannerVo);
        return JSON.toJSONString(result);

    }

    /**
     * 新增
     * @param banner
     * @return
     */
    @PostMapping("/add")
    public String addBanner(@Validated(Update.class) @RequestBody Banner banner){
        Result result = new Result();
        int count= bannerService.addBanner(banner);
        if(1 != count)BSUtil.isTrue(false, "新增失败");
        return JSON.toJSONString(result);
    }

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @PostMapping("/delete")
    public String deleteBanner(@RequestBody JSONObject jsonObject){
        Integer id = jsonObject.getInteger("id");
        Result result = new Result();
        int count= bannerService.deleteBanner(id);
        if(1 != count) BSUtil.isTrue(false, "删除失败");
        return  JSON.toJSONString(result);
    }

    /**
     * 修改
     * @param banner
     * @return
     */
    @PostMapping("/update")
    public String updateBanner(@Validated(Update.class) @RequestBody Banner banner){
        Result result = new Result();
        int count = bannerService.updateBanner(banner);
        if(1 != count)BSUtil.isTrue(false, "修改失败");
        return JSON.toJSONString(result);
    }




}
