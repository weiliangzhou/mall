package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Gift;
import com.zwl.model.po.UserGift;
import com.zwl.service.GiftService;
import com.zwl.service.UserGiftService;
import com.zwl.util.ThreadVariable;
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
    @Autowired
    private UserGiftService userGiftService;

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

    @PostMapping("/getGiftDetailById")
    public String getGiftDetailById(@RequestBody JSONObject jsonObject) {
        Long giftId = jsonObject.getLong("giftId");
        Gift gift = giftService.getGiftDetailById(giftId);
        Result result = new Result();
        result.setData(gift);
        return JSON.toJSONString(result);
    }

    /**
     * 分页查询用户兑换的礼品列表
     */
    @PostMapping("/findUserGiftListPage")
    public String findUserGiftListPage(@RequestBody JSONObject jsonObject) {
        String userId = ThreadVariable.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        PageInfo<UserGift> userGiftLists = userGiftService.findUserGiftListPage(userId, merchantId, pageSize, pageNum);
        Result result = new Result();
        result.setData(userGiftLists);
        return JSON.toJSONString(result);
    }

    /**
     * 兑换商品
     */
    @PostMapping("/exchangeGift")
    public Result exchangeGift(@RequestBody JSONObject jsonObject) {
        String userId = ThreadVariable.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        Long giftId = jsonObject.getLong("giftId");
        Long addressId = jsonObject.getLong("addressId");
        UserGift userGift = userGiftService.addUserExchangeGift(userId, merchantId, giftId, addressId);
        Result result = new Result();
        result.setData(userGift);
        return result;
    }

}
