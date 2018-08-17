package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Banner;
import com.zwl.model.vo.BannerVo;
import com.zwl.model.vo.PageClassInfoVo;
import com.zwl.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String  getBannerList(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Banner> bannerList =bannerService.getBannerList();
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
    public String insertBanner(Banner banner){
        Result result = new Result();
        int count= bannerService.insertBanner(banner);
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
    public String  deleteBanner(@RequestBody JSONObject jsonObject){
        Long id = jsonObject.getLong("id");
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
    public String updateBanner(Banner banner){
        Result result = new Result();
        int count = bannerService.updateBanner(banner);
        if(count != 1)
            BSUtil.isTrue(false, "修改失败");
        return  JSON.toJSONString(result);
    }




}
