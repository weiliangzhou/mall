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
    @PostMapping("/selectBanner")
    public String  selectBanner(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        String merchantId=jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page = PageHelper.startPage(pageNum, pageSize);
        Banner banner=new Banner();
        banner .setMerchantId(merchantId);
        List<Banner> bannerList =bannerService.selectBanner(banner);
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
    @RequestMapping("insertBanner")
    public String insertBanner(@Validated(Update.class) Banner banner){
        Result result = new Result();
        int count= bannerService.insert(banner);
        if(count != 1)
            BSUtil.isTrue(false, "新增失败");
        return JSON.toJSONString(result);
    }

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @RequestMapping("/deleteBanner")
    public String  deleteBanner(@Validated(Update.class) @RequestBody JSONObject jsonObject){
        int id = jsonObject.getInteger("id");
        Result result = new Result();
        int count= bannerService.deleteBanner(id);
        if(count != 1)
            BSUtil.isTrue(false, "删除失败");
        return  JSON.toJSONString(result);
    }

    /**
     * 修改
     * @param banner
     * @return
     */
    @PostMapping("/updateBanner")
    public String updateBanner(@Validated(Update.class) Banner banner){
        Result result = new Result();
        int count = bannerService.updateByPrimaryKey(banner);
        if(count != 1)
            BSUtil.isTrue(false, "修改失败");
        return  JSON.toJSONString(result);
    }




}
