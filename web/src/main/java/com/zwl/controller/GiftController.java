package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: GiftController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3111:22
 */
@RequestMapping("/wx/gift")
@RestController
public class GiftController {
    @Autowired
    private GiftService giftService;

    @PostMapping("/getGiftList")
    public String getGiftList(@RequestBody JSONObject jsonObject) {
        // queryType =0 首页推荐   queryType =1 全部
        Integer queryType = jsonObject.getInteger("queryType");
        String merchantId = jsonObject.getString("merchantId");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        if (pageSize != null && pageNum != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List giftList = giftService.getGiftList(queryType, merchantId);
        Result result = new Result();
        result.setData(giftList);
        return JSON.toJSONString(result);
    }


}
