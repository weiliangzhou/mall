package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.*;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.vo.OfflineActivityOrderVo;
import com.zwl.model.vo.UserVo;
import com.zwl.model.wxpay.IpKit;
import com.zwl.model.wxpay.StrKit;
import com.zwl.model.wxpay.WxPayVo;
import com.zwl.service.*;
import com.zwl.util.QRCodeUtil;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/wx/salon")
public class SalonController {
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;

    @PostMapping("/buy")
    public String offlineActivityBuy(HttpServletRequest request, @RequestBody OfflineActivityBuy offlineActivityBuy) {
        Result result = new Result();
        offlineActivityBuy.setOrderType(1);
        BuyResult buyResult = offlineActivityOrderService.offlineActivityBuy(offlineActivityBuy);
        String orderNo = buyResult.getOrderNo();
        Integer totalFee = buyResult.getTotalFee();
        String merchantId = buyResult.getMerchantId();
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        String gzhAppId = merchant.getGzAppId();
        String userId = ThreadVariable.getUserID();
        User user = userService.getByUserId(userId);
        String wxPayKey = merchant.getWxPayKey();
        String realIp = IpKit.getRealIp(request);
        if (StrKit.isBlank(realIp)) {
            realIp = "127.0.0.1";
        }
        WxPayVo wxPayVo = wxPayService.pay(realIp, user.getGzhOpenid(), orderNo, totalFee.toString(), gzhAppId, merchantId, wxPayKey);
        wxPayVo.setOrderNo(orderNo);
        result.setData(wxPayVo);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getSalonThemeList")
    public String getSalonThemeList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String queryType = jsonObject.getString("queryType");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer activityType = jsonObject.getInteger("activityType");
        if (pageSize != null && pageNum != null)
            PageHelper.startPage(pageNum, pageSize);
        List<OfflineActivityTheme> offlineActivityThemeList = offlineActivityThemeService.getOfflineActivityThemeListByQueryType(merchantId, queryType, activityType);
        Result result = new Result();
        result.setData(offlineActivityThemeList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getSalonThemeDetailByThemeId")
    public String getSalonThemeDetailByThemeId(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String merchantId = jsonObject.getString("merchantId");
        Integer themeId = jsonObject.getInteger("themeId");
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
        List<OfflineActivity> offlineActivityList = offlineActivityService.getOfflineActivityListByThemeId(merchantId, offlineActivityTheme.getId(), userId);
        if (null == offlineActivityList || offlineActivityList.isEmpty()) {
            Result result = new Result(ResultCodeEnum.FAIL ,"该沙龙暂未开始，敬请期待！");
            //如果该场沙龙主题没有沙龙，则状态设为5，前台显示为暂未开始
            offlineActivityTheme.setApplyStatus(5);
            result.setData(offlineActivityTheme);
            return JSON.toJSONString(result);
        }
        User user = userService.getByUserId(userId);
        Integer memberLevel = user.getMemberLevel();
        Long millisecond = System.currentTimeMillis();
        OfflineActivity offlineActivity = offlineActivityList.get(0);
        if (null != offlineActivity) {
            Integer limitCount = offlineActivity.getLimitCount();
            Integer buyCount = offlineActivity.getBuyCount();
            if (buyCount.intValue() >= limitCount.intValue()) {
                //如果购买人数大于等于限制人数，则状态设为1，前台显示为名额已满
                offlineActivityTheme.setApplyStatus(1);
            } else if (millisecond < offlineActivity.getApplyStartTime().getTime() || millisecond > offlineActivity.getApplyEndTime().getTime()) {
                //如果系统当前时间不在报名时间内，则状态设为2，前台显示为报名已结束
                offlineActivityTheme.setApplyStatus(2);
            } else if (memberLevel.intValue() < offlineActivity.getMinRequirement().intValue()) {
                //如果用户等级小于该场活动最低要求等级，则状态设为3，前台显示为未获得报名资格
                offlineActivityTheme.setApplyStatus(3);
            } else if (0 == offlineActivity.getIsRebuy().intValue()) {
                OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByUserIdAndOfflineActivityId(userId, offlineActivity.getId());
                if (null != offlineActivityCode) {
                    //如果用户已购买该场活动，则状态设为4，前台显示已报名
                    offlineActivityTheme.setApplyStatus(4);
                } else {
                    offlineActivityTheme.setApplyStatus(0);
                }
            } else {
                //上述条件都不满足，则状态设为4，前台显示为立即报名
                offlineActivityTheme.setApplyStatus(0);
            }
        }
        Result result = new Result();
        result.setData(offlineActivityTheme);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getSalonListByThemeId")
    public String getSalonListByThemeId(@RequestBody JSONObject jsonObject) {
        String userId = ThreadVariable.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        Integer activityThemeId = jsonObject.getInteger("activityThemeId");
        List<OfflineActivity> offlineActivityList = offlineActivityService.getOfflineActivityListByThemeId(merchantId, activityThemeId, userId);
        User user = userService.getByUserId(userId);
        Integer memberLevel = user.getMemberLevel();
        Long millisecond = System.currentTimeMillis();
        for (OfflineActivity offlineActivity : offlineActivityList) {
            Integer limitCount = offlineActivity.getLimitCount();
            Integer buyCount = offlineActivity.getBuyCount();
            if (buyCount.intValue() >= limitCount.intValue()) {
                //如果购买人数大于等于限制人数，则状态设为1，前台显示为名额已满
                offlineActivity.setApplyStatus(1);
            } else if (millisecond < offlineActivity.getApplyStartTime().getTime() || millisecond > offlineActivity.getApplyEndTime().getTime()) {
                //如果系统当前时间不在报名时间内，则状态设为2，前台显示为报名已结束
                offlineActivity.setApplyStatus(2);
            } else if (memberLevel.intValue() < offlineActivity.getMinRequirement().intValue()) {
                //如果用户等级小于该场活动最低要求等级，则状态设为3，前台显示为未获得报名资格
                offlineActivity.setApplyStatus(3);
            } else if (0 == offlineActivity.getIsRebuy().intValue()) {
                OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByUserIdAndOfflineActivityId(userId, offlineActivity.getId());
                if (null != offlineActivityCode) {
                    //如果用户已购买该场活动，则状态设为4，前台显示已报名
                    offlineActivity.setApplyStatus(4);
                } else {
                    offlineActivity.setApplyStatus(0);
                }
            } else {
                //上述条件都不满足，则状态设为4，前台显示为立即报名
                offlineActivity.setApplyStatus(0);
            }
        }
        Result result = new Result();
        result.setData(offlineActivityList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getMySLActivityOrderList")
    public String getMySLActivityOrderList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String activityThemeId = jsonObject.getString("activityThemeId");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        if (pageSize != null && pageNum != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        String userId = ThreadVariable.getUserID();
        //String userId = "123";
        List<OfflineActivityOrderVo> offlineActivityOrderVoList = offlineActivityOrderService.getMySLActivityOrderList(userId, merchantId, activityThemeId);
        Result result = new Result();
        result.setData(offlineActivityOrderVoList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getSLActivityOrderDetail")
    public String getSLActivityOrderDetail(@RequestBody JSONObject jsonObject) {
        String orderNo = jsonObject.getString("orderNo");
        OfflineActivityOrderVo offlineActivityOrderVo = offlineActivityOrderService.getSLActivityOrderDetail(orderNo);
        Result result = new Result();
        result.setData(offlineActivityOrderVo);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getSLUserInfo")
    public String getSLUserInfo(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String slReferrer = jsonObject.getString("slReferrer");
        String userId = ThreadVariable.getUserID();
        //String userId = "579bbf4031c041efa65ded6d41fbc742";
        UserVo userVo = userService.getSLUserInfo(merchantId, userId);
        if (StringUtils.isNotBlank(slReferrer)) {
            User user = userService.getByUserId(slReferrer);
            if(null != user){
                if(user.getMemberLevel() >=1){
                    userVo.setSlReferrer(slReferrer);
                    userVo.setSlReferrerName(user.getRealName());
                    userVo.setSlReferrerPhone(user.getRegisterMobile());
                }else{
                    userVo.setSlReferrerName("单影");
                    userVo.setSlReferrerPhone("18896815868");
                    userVo.setSlReferrer("25c3c33de8e74f3482aec08d2ab6206c");
                }
            }else{
                userVo.setSlReferrerName("单影");
                userVo.setSlReferrerPhone("18896815868");
                userVo.setSlReferrer("25c3c33de8e74f3482aec08d2ab6206c");
            }
        } else {
            userVo.setSlReferrerName("单影");
            userVo.setSlReferrerPhone("18896815868");
            userVo.setSlReferrer("25c3c33de8e74f3482aec08d2ab6206c");
        }
        Result result = new Result();
        result.setData(userVo);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getSLQrCode")
    public String getSLQrCode(@RequestBody JSONObject jsonObject) {
        String url = jsonObject.getString("url");
        Integer slId = jsonObject.getInteger("slId");
        String merchantId = jsonObject.getString("merchantId");
        String userId = ThreadVariable.getUserID();
        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(slId);
        String qrUrl = stringRedisTemplate.boundValueOps(userId + "_sl_QrCode_" + slId).get();
        if (StringUtils.isBlank(qrUrl)) {
            String smallImage = QRCodeUtil.createQrCode(url + "&slReferrer=" + userId, null, null);
            User user = userService.getByUserId(userId);
            UserInfo userInfo = userInfoService.getByUserId(userId);
            String userLogo = user.getLogoUrl() == null ? "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180911/6a989ec302994c6c98c2d4810f9fbcb2.png" : user.getLogoUrl();
            String nickNameOrPhone = StringUtils.isBlank(userInfo.getNickName()) ? user.getRegisterMobile() : userInfo.getNickName();
            Integer themeId = offlineActivity.getActivityThemeId();
            OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
            String slName = offlineActivityTheme.getThemeName();
            Date slStartTime = offlineActivity.getActivityStartTime();
            Date slEndTime = offlineActivity.getActivityEndTime();
            SimpleDateFormat sdf_yMd_Hm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat sdf_Hms = new SimpleDateFormat("HH:mm");
            String slStartTimeStr = sdf_yMd_Hm.format(slStartTime);
            String slEndTimeStr = sdf_Hms.format(slEndTime);
            String slStr = "时间: "+slStartTimeStr + "-" + slEndTimeStr;
            try {
                qrUrl = QRCodeUtil.SlMergeImage("http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20181101/5cf42276a5c944429f1796e25305bb80.png",
                        smallImage, 380, 690,
                        userLogo, 200, 100,
                        nickNameOrPhone, 200, 350, Color.white,
                        slName, 693, 670, Color.orange,
                        slStr, 873, 900, Color.orange);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringRedisTemplate.boundValueOps(userId + "_sl_QrCode_" + slId).set(qrUrl, 30, TimeUnit.DAYS);
        }
        log.info("userId:" + userId + "------二维码" + qrUrl);
        Result result = new Result();
        result.setData(qrUrl);
        return JSON.toJSONString(result);

    }
}
