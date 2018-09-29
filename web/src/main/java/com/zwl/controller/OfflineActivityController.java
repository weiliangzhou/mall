package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.po.*;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.vo.SignInVo;
import com.zwl.model.wxpay.*;
import com.zwl.service.*;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @RequestMapping(value = "/pay_notify_offline.do", method = {RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String pay_notify(HttpServletRequest request) {
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        String xmlMsg = HttpKit.readData(request);
        log.info("微信支付回调结果" + xmlMsg);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
        String result_code = params.get("result_code");
        String return_msg = params.get("return_msg");
        /////////////////////////////以下是附加参数///////////////////////////////////
//        商家数据包
        String attach = params.get("attach");
        if (("SUCCESS").equals(result_code)) {
            //更新订单信息
            log.info("原订单信息:" + attach);
            String appid = params.get("appid");
            //商户号
            String mch_id = params.get("mch_id");
            String sign = params.get("sign");
            String openid = params.get("openid");
            // 总金额
            String total_fee = params.get("total_fee");
            //现金支付金额
            String cash_fee = params.get("cash_fee");
            // 微信支付订单号
            String transaction_id = params.get("transaction_id");
            // 商户订单号
            String out_trade_no = params.get("out_trade_no");
            // 支付完成时间，格式为yyyyMMddHHmmss
            String time_end = params.get("time_end");
            // 根据订单号判断是否是线下活动，如果是则不返佣
            OfflineActivityOrder offlineActivityOrder = offlineActivityOrderService.findOrderByOrderNo(out_trade_no);
            //如果order状态为支付成功，则不更新
            Integer orderStatus = offlineActivityOrder.getOrderStatus();
            //判断支付金额是否与订单实际支付金额一致，不一致则返回错误，防止被恶意刷单攻击
            //判断支付回调签名是否跟发送的签名一致，不一致则返回错误，防止被恶意刷单攻击
            log.info("回调订单签名--------->" + sign);
//                根据商户号获取支付key
            Merchant merchant = merchantService.getMerchantByMerchantId(mch_id);
            boolean checkSign = PaymentKit.isWechatSign(params, merchant.getWxPayKey());
            if (!checkSign) {
                BSUtil.isTrue(false, "签名错误!");
                //发送通知等
                Map<String, String> xml = new HashMap<>();
                xml.put("return_code", "ERROR");
                xml.put("return_msg", "ERROR");
                return PaymentKit.toXml(xml);
            }
            Integer orderActualMoney_temp = offlineActivityOrder.getActualMoney();
            if (Integer.parseInt(total_fee) < orderActualMoney_temp)
                BSUtil.isTrue(false, "支付失败");

            return "";
        }


        return "";
    }
}