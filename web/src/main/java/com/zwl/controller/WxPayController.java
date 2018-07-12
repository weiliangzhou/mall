package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.baseresult.Result;
import com.zwl.exception.BSUtil;
import com.zwl.model.MaidInfo;
import com.zwl.model.Order;
import com.zwl.model.User;
import com.zwl.service.*;
import com.zwl.wxpay.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class WxPayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private MaidInfoService maidInfoService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private WxSenderService wxSenderService;


    private SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyyMMddHHmmss");

    public static WxPayH5 getApiConfig() {
        return WxPayH5.New()
                .setAppId(WxConstans.APPID)
                .setMchId(WxConstans.MCHID)
                .setPaternerKey(WxConstans.PARTNERKEY);
    }

    /**
     * 微信H5 支付
     * 注意：必须再web页面中发起支付且域名已添加到开发配置中
     */
    @RequestMapping(value = "/wx/pay.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String wapPay(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        String realIp = IpKit.getRealIp(request);
        if (StrKit.isBlank(realIp)) {
            realIp = "127.0.0.1";
        }
        String openId = jsonObject.getString("openId");
        String orderNo = jsonObject.getString("orderNo");
        String totalFee = jsonObject.getString("totalFee");
        Map resultMap = wxPayService.pay(realIp, openId, orderNo, totalFee);
        Result result_return = new Result();
        result_return.setData(resultMap);
        return JSON.toJSONString(result_return);
    }


    @RequestMapping(value = "/wx/pay_notify.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
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
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");
        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        // 避免已经成功、关闭、退款的订单被再次更新
        WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig());
        if (PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())) {
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
                Order order = orderService.findOrderByOrderNo(out_trade_no);
                //如果order状态为支付成功，则不更新
                Integer status = order.getOrderStatus();
                if (1 != status) {
                    int count = orderService.updateOrder(out_trade_no, time_end, transaction_id);
                    if (count != 1)
                        BSUtil.isTrue(false, "异步支付更新失败");
                    //购买成功之后，更新用户会员等级，等级名称，会员到期时间
                    String userId = order.getUserId();
                    Integer validityTime = order.getValidityTime();
                    User user = wxUserService.getUserById(userId);
//                    支付完成时间，格式为yyyyMMddHHmmss
                    try {
                        Date paymentTime = sdf_yMdHms.parse(time_end);
                        String memberValidityTime = sdf_yMdHms.format(paymentTime.getTime() + (long) validityTime * 24 * 60 * 60 * 1000);
                        user.setExpiresTime(sdf_yMdHms.parse(memberValidityTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Integer memberLevel = order.getLevel();
                    user.setMemberLevel(memberLevel);
                    user.setLevelName(order.getLevelName());
                    log.info("回调支付成功开始更新用户等级" + user);
                    wxUserService.updateUserById(user);
                    //订单支付成功，返佣给推荐人
                    //返佣规则：同级或者下级，高于当前级别是不返佣
                    log.info("回调支付成功，开始分佣");
                    String referrerId = user.getReferrer();
                    if (StringUtils.isNotBlank(referrerId)) {
                        User referrerUser = wxUserService.getUserById(referrerId);
                        if (null != referrerUser) {
                            //查询当前用户有效会员等级并且小于等于推荐人的有效会员等级才可以返佣
//                            Integer memberLevel=wxUserService.getMemberLevel(userId);
                            Integer referrerLevel = wxUserService.getMemberLevel(referrerId);
                            if (null != referrerLevel & referrerLevel >= memberLevel) {
                                String merchantId = order.getMerchantId();
                                String orderNo = order.getOrderNo();
                                Integer orderActualMoney = order.getActualMoney();
                                //通过userId获取推荐人对应的分佣比例
                                Integer maidPercent = wxUserService.getMaidPercentByUserId(userId);
//                                Integer maidPercent = order.getMaidPercent();
                                Integer maidMoney = orderActualMoney * maidPercent;
                                Long productId = order.getProductId();
                                String productName = order.getProductName();
                                Integer level = order.getLevel();
                                String levelName = order.getLevelName();
                                MaidInfo maidInfo = new MaidInfo();
                                maidInfo.setOrderNo(orderNo);
                                //分佣发送给推荐人
                                maidInfo.setUserId(referrerUser.getUserId());
                                maidInfo.setMaidMoney(maidMoney);
                                maidInfo.setMaidPercent(maidPercent);
                                maidInfo.setOrderActualMoney(orderActualMoney);
                                maidInfo.setMerchantId(merchantId);
                                maidInfo.setProductId(productId);
                                maidInfo.setProductName(productName);
                                maidInfo.setLevel(level);
                                maidInfo.setLevelName(levelName);
                                log.info("回调支付成功，分佣信息" + maidInfo);
                                int madiInfoCount = maidInfoService.save(maidInfo);
                                if (madiInfoCount == 0)
                                    BSUtil.isTrue(false, "分佣失败");
                                log.info("回调支付成功，结束分佣");
                                //发送订单购买公众号提醒
                                wxSenderService.sendBuyMsg(openid, productName, orderActualMoney);

                            }

                        }
                    }


                }
                //发送通知等
                Map<String, String> xml = new HashMap<String, String>();
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                return PaymentKit.toXml(xml);
            }
        }

        return null;
    }

}
