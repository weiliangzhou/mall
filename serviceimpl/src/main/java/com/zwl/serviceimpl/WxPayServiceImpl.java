package com.zwl.serviceimpl;

import com.zwl.model.groups.AdminPay;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.wxpay.*;
import com.zwl.service.WithdrawService;
import com.zwl.service.WxPayService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxPayServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1014:24
 */
@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    private WithdrawService withdrawService;
    private SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public WxPayVo pay(String realIp, String openId, String orderNo, String totalFee) {
        log.info("开始微信支付");

        //此域名必须在商户平台--"产品中心"--"开发配置"中添加
//        h5_info.setWap_url("https://www.zzw777.com");
//        h5_info.setWap_name("腾讯充值");
//        sceneInfo.setH5_info(h5_info);
        Calendar cal = Calendar.getInstance();
        String timeStart = sdf_yMdHms.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, +5);
        String timeExpire = sdf_yMdHms.format(cal.getTime());
        Map payMap=new HashMap();
        payMap.put("appid",WxConstans.XCX_APPID);
        payMap.put("mch_id",WxConstans.MCHID);
        payMap.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        payMap.put("body", "测试，二师兄");
        payMap.put("attach", "测试，二师兄");
        payMap.put("out_trade_no", orderNo);
        payMap.put("total_fee", totalFee);
        payMap.put("spbill_create_ip", realIp);
        payMap.put("time_start", timeStart);
        payMap.put("time_expire", timeExpire);
        payMap.put("notify_url", WxConstans.USER_NOTIFY_URL);
        payMap.put("trade_type", "JSAPI");
        payMap.put("openid", openId);
        payMap.put("sign", PaymentKit.createSign(payMap, WxConstans.PARTNERKEY));
        //获取微信返回的结果
        log.info("开始发送微信支付xml--->" + PaymentKit.toXml(payMap));
        String xmlResult = HttpsUtils.sendPost(WxConstans.PAY_URL, PaymentKit.toXml(payMap)).toString();
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.info("结束发送微信支付xml--->" + result);
        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            log.error("return_code>" + return_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            log.error("result_code>" + result_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String trade_type = result.get("trade_type");//交易类型  H5支付固定传MWEB
        String prepay_id = result.get("prepay_id");//预支付交易会话标识
        String mweb_url = result.get("mweb_url");//支付跳转链接
//        log.info("prepay_id:" + prepay_id + " mweb_url:" + mweb_url);
//        String appid = result.get("appid");//公众账号ID
//        String mch_id = result.get("mch_id");//商户号
//        String device_info = result.get("device_info");//设备号
//        String nonce_str = result.get("nonce_str");
//        String sign = result.get("sign");
//        String err_code = result.get("err_code");//错误代码
//        String err_code_des = result.get("err_code_des");//错误代码描述

//        timeStamp	String	是	时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
//        nonceStr	String	是	随机字符串，长度为32个字符以下。
//        package	String	是	统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=*
//        signType	String	是	签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
//        paySign	String	是	签名,具体签名方案参见微信公众号支付帮助文档;
        WxPayVo wxPayVo=new WxPayVo();
        wxPayVo.setTimeStamp(sdf_yMdHms.format(new Date()));
        wxPayVo.setNonceStr(payMap.get("nonceStr").toString());
        wxPayVo.setPackageStr(prepay_id);
        wxPayVo.setPaySign( payMap.get("sign").toString());
        return wxPayVo;
    }

    @Override
    public void adminPay(@Validated(AdminPay.class) AdminPayVo adminPayVo) {
        log.info("开始企业付款");
        String openId = adminPayVo.getOpenId();
        String realName = adminPayVo.getRealName();
        Integer amount = adminPayVo.getAmount();
        String desc = adminPayVo.getDesc();
        String realIp = adminPayVo.getRealIp();
        Map<String, String> params = WxAdminPay.New()
                .setMch_appid(WxConstans.APPID)
                .setMchid(WxConstans.MCHID)
                .setPartner_trade_no("提现号")
                .setOpenid(openId)
                .setRe_user_name(realName)
                .setAmount(amount)//企业付款金额，单位为分
                .setDesc(desc)
                .setSpbill_create_ip(realIp)
                .setPaternerKey(WxConstans.PARTNERKEY)
                .build();

        //获取微信返回的结果
        log.info("开始发送企业付款xml--->" + PaymentKit.toXml(params));
        String xmlResult = HttpsUtils.sendPost(WxConstans.ADMIN_PAY_URL, PaymentKit.toXml(params)).toString();
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.info("结束发送企业付款xml--->" + result);
        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            log.error("return_code>" + return_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            log.error("result_code>" + result_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
//        商户订单号	partner_trade_no	是	1217752501201407033233368018	String(32)	商户订单号，需保持历史全局唯一性(只能是字母或者数字，不能包含有符号)
//        微信订单号	payment_no	是	1007752501201407033233368018	String	企业付款成功，返回的微信订单号
//        微信支付成功时间	payment_time	是	2015-05-19 15：26：59	String	企业付款成功时间
        String partner_trade_no = result.get("partner_trade_no");
        String payment_no = result.get("payment_no");
        String payment_time = result.get("payment_time");
        log.info("商户订单号:" + partner_trade_no + " 企业付款成功，返回的微信订单号:" + payment_no + "企业付款成功时间" + payment_time);
        //更新提现信息
        int updateCount = withdrawService.updateByWithdrawId(partner_trade_no, partner_trade_no, payment_no);
        if (0 == updateCount)
            BSUtil.isTrue(false, "微信付款成功，更新数据库异常");
    }

}
