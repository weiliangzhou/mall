package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Gift;
import com.zwl.model.po.User;
import com.zwl.model.po.UserGift;
import com.zwl.model.po.UserInfo;
import com.zwl.service.GiftService;
import com.zwl.service.UserGiftService;
import com.zwl.service.UserInfoService;
import com.zwl.service.UserService;
import com.zwl.util.QRCodeUtil;
import com.zwl.util.ThreadVariable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;

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
        String merchantId = jsonObject.getString("merchantId");
        String userId = jsonObject.getString("userId");
        UserGift userGift = null;
        if (userId != null) {
            //查询是否已经购买过
            userGift = userGiftService.getUserGiftByGiftId(userId, merchantId, giftId);
        }
        if (userGift != null) {
            gift.setBuyFlag(1);
        } else {
            gift.setBuyFlag(0);
        }
        List imgList = new ArrayList();
        if (gift != null) {
            String img1 = gift.getGiftViceImg1();
            String img2 = gift.getGiftViceImg2();
            String img3 = gift.getGiftViceImg3();
            if (img1 != null) {
                imgList.add(img1);
            }
            if (img2 != null) {
                imgList.add(img2);
            }
            if (img3 != null) {
                imgList.add(img3);
            }

        }
        gift.setImgList(imgList);
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
     * 获取用户的订单详情用户前端查看快递单号
     */
    @PostMapping("/getUserGiftById")
    public Result getUserGiftById(@RequestBody JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        UserGift userGift = userGiftService.getUserGiftById(id);
        Result result = new Result();
        result.setData(userGift);
        return result;
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

    @PostMapping("/getGiftQrCode")
    public String getGiftQrCode(@RequestBody JSONObject jsonObject) {
        Long giftId = jsonObject.getLong("giftId");
        String url = jsonObject.getString("url");
        String userId = ThreadVariable.getUserID();
        String qrUrl = stringRedisTemplate.boundValueOps(userId + "_gift_QrCode").get();
        if (StringUtils.isBlank(qrUrl)) {
            String smallImage = QRCodeUtil.createQrCode(url + "&referrer=" + userId + giftId, null, null);
            User user = userService.getByUserId(userId);
            UserInfo userInfo = userInfoService.getByUserId(userId);
            String userLogo = user.getLogoUrl() == null ? "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180911/6a989ec302994c6c98c2d4810f9fbcb2.png" : user.getLogoUrl();
            String nickNameOrPhone = StringUtils.isBlank(userInfo.getNickName()) ? user.getRegisterMobile() : userInfo.getNickName();
            Gift gift = giftService.getGiftDetailById(giftId);
            String giftMainImg = gift.getGiftMainImg();
            String memberLevel = user.getLevelName();

            try {
                qrUrl = QRCodeUtil.giftMergeImage("http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20181101/b727dc1a4eb342dcabc90b4df03a9ba5.png", smallImage, 340, 710,
                        userLogo, 88, 715, nickNameOrPhone, 178, 770, Color.BLACK, memberLevel, 178, 790, Color.gray, giftMainImg, 88, 470);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringRedisTemplate.boundValueOps(userId + "_gift_QrCode" + giftId).set(qrUrl, 30, TimeUnit.DAYS);
        }
        Result result = new Result();
        result.setData(qrUrl);
        return JSON.toJSONString(result);

    }
}
