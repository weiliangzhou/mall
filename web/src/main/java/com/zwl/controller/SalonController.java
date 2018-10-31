package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.po.*;
import com.zwl.model.vo.*;
import com.zwl.model.wxpay.IpKit;
import com.zwl.model.wxpay.StrKit;
import com.zwl.model.wxpay.WxPayVo;
import com.zwl.service.*;
import com.zwl.util.DictUtil;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/wx/salon")
public class SalonController {
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityOperatorService offlineActivityOperatorService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private WxPayService wxPayService;

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
        String merchantId = jsonObject.getString("merchantId");
        Integer themeId = jsonObject.getInteger("themeId");
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
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
        Result result = new Result();
        result.setData(offlineActivityList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getMySLActivityOrderList")
    public String getMySLActivityOrderList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        if (pageSize != null && pageNum != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        String userId = ThreadVariable.getUserID();
        //String userId = "123";
        List<OfflineActivityOrderVo> offlineActivityOrderVoList = offlineActivityOrderService.getMySLActivityOrderList(userId, merchantId);
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
        if(StringUtils.isNotBlank(slReferrer)) {
            User user = userService.getByUserId(slReferrer);
            userVo.setSlReferrerName(user.getRealName());
            userVo.setSlReferrerPhone(user.getRegisterMobile());
        }else{
            userVo.setSlReferrerName("单影");
            userVo.setSlReferrerPhone("18896815868");
            userVo.setSlReferrer("25c3c33de8e74f3482aec08d2ab6206c");
        }
        Result result = new Result();
        result.setData(userVo);
        return JSON.toJSONString(result);
    }
}
