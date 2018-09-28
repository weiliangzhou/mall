package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.po.Merchant;
import com.zwl.model.po.OfflineActivity;
import com.zwl.model.po.OfflineActivityCode;
import com.zwl.model.po.User;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.vo.SignInVo;
import com.zwl.model.wxpay.IpKit;
import com.zwl.model.wxpay.StrKit;
import com.zwl.model.wxpay.WxPayVo;
import com.zwl.service.*;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 二师兄超级帅
 * @Title: 线下活动
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2617:14
 */
@RestController
@Slf4j
@RequestMapping("/wx/offlineActivity")
public class OfflineActivityController {
    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;

    @PostMapping("/buy")
    public String offlineActivityBuy(HttpServletRequest request, @RequestBody OfflineActivityBuy offlineActivityBuy) {
        Result result = new Result();
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

    @PostMapping("/offlineLogin")
    public Result signIn(@RequestBody JSONObject jsonObject) {
        //
        //

        Result result = new Result();
        return result;

    }

    @PostMapping("/signIn")
    public Result signIn(@Validated(Buy.class) @RequestBody SignInVo signInVo) {
        //获取操作员
        String operator = signInVo.getOperator();
        //通过操作员匹配
        //ss_offline_activity_operator

        if (!"weigu".equals(operator))
            BSUtil.isTrue(false, "非法操作!");

        //获取code
        String activityCode = signInVo.getActivityCode();
        //通过code 做一个比对
        //如果正确则更新ss_offline_activity_code
        OfflineActivityCode offlineActivity = offlineActivityCodeService.getOneByActivityCode(activityCode);
        if (offlineActivity == null)
            BSUtil.isTrue(false, "非法code!");
        else {
            OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(activityCode);
            if (offlineActivityCode.getIsUsed() == 1)
                BSUtil.isTrue(false, "签到码已被使用！");
            Integer activityId = offlineActivityCode.getActivityId();
            //需要判断当前下线活动  是否在活动期间
            //如果在的话 则更新
            //如果不在  则报错 签到活动已经到期
            OfflineActivity offlineActivityCheckTime = offlineActivityService.getOneByActivityIdAndCheckTime(activityId);
            if (offlineActivityCheckTime == null)
                BSUtil.isTrue(false, "活动未开始或者已过期！");

            offlineActivityCodeService.updatePassByActivityCode(activityCode);
        }
        Result result = new Result();
        return result;

    }


}
