package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.po.*;
import com.zwl.model.vo.ActivityCodeDetail;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.vo.SignInVo;
import com.zwl.model.wxpay.IpKit;
import com.zwl.model.wxpay.StrKit;
import com.zwl.model.wxpay.WxPayVo;
import com.zwl.service.*;
import com.zwl.util.MemberLevelUtil;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Autowired
    private OfflineActivityOperatorService offlineActivityOperatorService;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;

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
    public Result operatorSignIn(@RequestBody JSONObject jsonObject) {
        String operator = jsonObject.getString("operator");
        String password = jsonObject.getString("password");
        String merchantId = jsonObject.getString("merchantId");
        OfflineActivityOperator offlineActivityOperator = new OfflineActivityOperator();
        offlineActivityOperator.setOperator(operator);
        offlineActivityOperator.setPassword(password);
        offlineActivityOperator.setMerchantId(merchantId);
        offlineActivityOperator.setAvailable(1);
        OfflineActivityOperator offlineActivityOperator1 = offlineActivityOperatorService.selectByOperatorAndPassword(offlineActivityOperator);
        if (offlineActivityOperator1 == null) BSUtil.isTrue(false, "操作员登陆失败");
        Result result = new Result();
        return result;

    }

    @PostMapping("/getOfflineActivityThemeList")
    public String getOfflineActivityThemeList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String queryType = jsonObject.getString("queryType");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        if (pageSize != null && pageNum != null)
            PageHelper.startPage(pageNum, pageSize);
        List<OfflineActivityTheme> offlineActivityThemeList = offlineActivityThemeService.getOfflineActivityThemeListByQueryType(merchantId, queryType);
        Result result = new Result();
        result.setData(offlineActivityThemeList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getOfflineActivityThemeDetailByThemeId")
    public String getOfflineActivityThemeDetailByThemeId(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String themeId = jsonObject.getString("themeId");
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
        Result result = new Result();
        result.setData(offlineActivityTheme);
        return JSON.toJSONString(result);
    }

    @PostMapping("/getOfflineActivityListByThemeId")
    public String getOfflineActivityListByThemeId(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String themeId = jsonObject.getString("themeId");
        List<OfflineActivity> offlineActivityList = offlineActivityService.getOfflineActivityListByThemeId(merchantId, themeId);
        Result result = new Result();
        result.setData(offlineActivityList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/signIn")
    public Result signIn(@Validated(Buy.class) @RequestBody SignInVo signInVo) {
        //获取操作员
        String operator = signInVo.getOperator();
        //通过操作员匹配
        //ss_offline_activity_operator
        OfflineActivityOperator offlineActivityOperator = offlineActivityOperatorService.getOneByOperator(operator);
        if (null == offlineActivityOperator) {
            Result result = new Result();
            result.setCode("800");
            result.setMessage("非法操作！");
            return result;
        }
        //获取code
        String activityCode = signInVo.getActivityCode();
        //通过code 做一个比对
        //如果正确则更新ss_offline_activity_code
        OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(activityCode);
        if (offlineActivityCode == null)
            BSUtil.isTrue(false, "非法code!");
        else {
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

    @PostMapping("/getActivityCodeDetail")
    public String getActivityCodeDetail(@RequestBody JSONObject jsonObject) {
        String activityCode = jsonObject.getString("activityCode");
        OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(activityCode);
        if (offlineActivityCode == null)
            BSUtil.isTrue(false, "非法code!");
        else {
            Integer activityId = offlineActivityCode.getActivityId();
            OfflineActivity offlineActivityCheckTime = offlineActivityService.getOneByActivityIdAndCheckTime(activityId);
            if (offlineActivityCheckTime == null)
                BSUtil.isTrue(false, "活动未开始或者已过期！");
            else {
                //查询订单号
                OfflineActivityOrder offlineActivityOrder = offlineActivityOrderService.findOrderByActivityCode(activityCode);
                ActivityCodeDetail activityCodeDetail = new ActivityCodeDetail();
                activityCodeDetail.setRealName(offlineActivityOrder.getRealName());
                User user = userService.getByUserId(offlineActivityOrder.getUserId());
                Integer memberLevel = user.getMemberLevel();
                activityCodeDetail.setMemberLevel(MemberLevelUtil.getMemberLevelStr(memberLevel));
                activityCodeDetail.setSex(offlineActivityOrder.getSex());
                activityCodeDetail.setPhone(offlineActivityOrder.getPhone());
                activityCodeDetail.setIdCardNum(offlineActivityOrder.getIdCardNum());
                //第一次购买则显示初次
                //否则就是复训
                //通过userid 查看当前用户 是否已经购买过该主题
                Integer themeId = offlineActivityCheckTime.getActivityThemeId();
                String userId = offlineActivityCode.getUserId();
                Integer userBuyCount = offlineActivityCodeService.getBuyCountByUserIdAndThemeId(userId, themeId);
                if (userBuyCount == 1)
                    activityCodeDetail.setStatus("初次");
                else
                    activityCodeDetail.setStatus("复训");
                activityCodeDetail.setActivityAddress(offlineActivityCheckTime.getActivityAddress());
                activityCodeDetail.setActivityStartTime(offlineActivityCheckTime.getActivityStartTime());
                activityCodeDetail.setActivityEndTime(offlineActivityCheckTime.getActivityEndTime());
                activityCodeDetail.setLogoUrl(user.getLogoUrl()==null?"http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180930/be406a40059343eb8e4952300e063149.jpg":user.getLogoUrl());
                Result result = new Result();
                result.setData(activityCodeDetail);
                return JSON.toJSONString(result);
            }


        }
        return "";

    }


}