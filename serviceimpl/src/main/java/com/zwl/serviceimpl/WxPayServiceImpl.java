package com.zwl.serviceimpl;

import com.zwl.model.wxpay.PaymentKit;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.model.wxpay.WxPayH5;
import com.zwl.model.wxpay.WxPayVo;
import com.zwl.service.WxPayService;
import com.zwl.util.HttpsUtils;
import com.zwl.util.PayNotifyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private PayNotifyProperties payNotifyProperties;
    private SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public WxPayVo androidPay(String realIp, String openId, String orderNo, String totalFee, String appid, String mch_id, String wxPayKey) {
        log.info("开始微信支付realIp-->" + realIp + "openId-->" + openId + "orderNo-->" + orderNo + "totalFee-->" + totalFee + "appid-->" + appid + "mch_id-->" + mch_id + "wxPayKey->" + wxPayKey);

        //此域名必须在商户平台--"产品中心"--"开发配置"中添加
//        h5_info.setWap_url("https://www.zzw777.com");
//        h5_info.setWap_name("腾讯充值");
//        sceneInfo.setH5_info(h5_info);
        Calendar cal = Calendar.getInstance();
        String timeStart = sdf_yMdHms.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, +5);
        String timeExpire = sdf_yMdHms.format(cal.getTime());
        Map payMap = new HashMap();
        payMap.put("appid", appid);
        payMap.put("mch_id", mch_id);
        payMap.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        payMap.put("body", "东遥课堂");
        payMap.put("attach", "东遥课堂");
        payMap.put("out_trade_no", orderNo);
        payMap.put("total_fee", totalFee);
        payMap.put("spbill_create_ip", "127.0.0.1");
        payMap.put("time_start", timeStart);
        payMap.put("time_expire", timeExpire);
        payMap.put("notify_url", payNotifyProperties.getPayNotifyUrl());
        payMap.put("trade_type", "JSAPI");
        payMap.put("openid", openId);
        String sign = PaymentKit.createSign(payMap, wxPayKey);
        payMap.put("sign", sign);
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
        String date = sdf_yMdHms.format(new Date());
        Map payResult = new HashMap();
        payResult.put("appId", appid);
        payResult.put("timeStamp", date);
        payResult.put("nonceStr", payMap.get("nonce_str").toString());
        payResult.put("package", "prepay_id=" + prepay_id);
        payResult.put("signType", "MD5");
        String paySign = PaymentKit.createSign(payResult, wxPayKey);
        payResult.put("paySign", paySign);
        log.info("支付原订单签名--------->" + paySign);
        WxPayVo wxPayVo = new WxPayVo();
        wxPayVo.setTimeStamp(date);
        wxPayVo.setNonceStr(payMap.get("nonce_str").toString());
        wxPayVo.setPackageStr("prepay_id=" + prepay_id);
        wxPayVo.setSignType("MD5");
        wxPayVo.setPaySign(payResult.get("paySign").toString());
        return wxPayVo;
    }

    @Override
    public WxPayVo pay(String realIp, String openId, String orderNo, String totalFee, String gzhAppId, String mch_id, String wxPayKey) {
        log.info("开始微信支付realIp-->" + realIp + "openId-->" + openId + "orderNo-->" + orderNo + "totalFee-->" + totalFee + "gzhAppId-->" + gzhAppId + "mch_id-->" + mch_id + "wxPayKey->" + wxPayKey);

        //此域名必须在商户平台--"产品中心"--"开发配置"中添加
//        h5_info.setWap_url("https://xcx.wegoo.cn");
//        h5_info.setWap_name("腾讯充值");
//        sceneInfo.setH5_info(h5_info);
        Calendar cal = Calendar.getInstance();
        String timeStart = sdf_yMdHms.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, +5);
        String timeExpire = sdf_yMdHms.format(cal.getTime());
        Map<String, String> params = WxPayH5.New()
                .setAppId(gzhAppId)
                .setMchId(mch_id)
                .setBody("东遥课堂")
                .setOutTradeNo(orderNo)
                .setTotalFee(totalFee)
                .setTimeStart(timeStart)
                .setTimeExpire(timeExpire)
                .setNotifyUrl(payNotifyProperties.getPayNotifyUrl())
                .setSpbillCreateIp("127.0.0.1")
                .setTradeType("JSAPI")
                .setOpenId(openId)
                .setSceneInfo("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"admin.com\",\"wap_name\": \"东遥课堂\"}}")
                .setAttach("东遥课堂")
                .setPaternerKey(wxPayKey)
                .build();
        //获取微信返回的结果
        log.info("开始发送微信支付xml--->" + PaymentKit.toXml(params));
        String xmlResult = HttpsUtils.sendPost(WxConstans.PAY_URL, PaymentKit.toXml(params)).toString();
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
        String date = sdf_yMdHms.format(new Date());
        Map payResult = new HashMap();
        payResult.put("appId", gzhAppId);
        payResult.put("timeStamp", date);
        payResult.put("nonceStr", params.get("nonce_str"));
        payResult.put("package", "prepay_id=" + prepay_id);
        payResult.put("signType", "MD5");
        String paySign = PaymentKit.createSign(payResult, wxPayKey);
        payResult.put("paySign", paySign);
        WxPayVo wxPayVo = new WxPayVo();
        wxPayVo.setTimeStamp(date);
        wxPayVo.setNonceStr(params.get("nonce_str"));
        wxPayVo.setPackageStr("prepay_id=" + prepay_id);
        wxPayVo.setSignType("MD5");
        wxPayVo.setPaySign(payResult.get("paySign").toString());
        wxPayVo.setMweb_url(mweb_url);
        wxPayVo.setAppid(gzhAppId);
        return wxPayVo;
    }
    @Override
    public WxPayVo H5Pay(String realIp, String openId, String orderNo, String totalFee, String gzhAppId, String mch_id, String wxPayKey,String redirectUrl) {
        log.info("开始微信支付realIp-->" + realIp + "openId-->" + openId + "orderNo-->" + orderNo + "totalFee-->" + totalFee + "gzhAppId-->" + gzhAppId + "mch_id-->" + mch_id + "wxPayKey->" + wxPayKey);

        //此域名必须在商户平台--"产品中心"--"开发配置"中添加
//        h5_info.setWap_url("https://xcx.wegoo.cn");
//        h5_info.setWap_name("腾讯充值");
//        sceneInfo.setH5_info(h5_info);
        Calendar cal = Calendar.getInstance();
        String timeStart = sdf_yMdHms.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, +5);
        String timeExpire = sdf_yMdHms.format(cal.getTime());
        Map<String, String> params = WxPayH5.New()
                .setAppId(gzhAppId)//先写死"wx32966989d0bc098d"
                .setMchId(mch_id)//"1509688041"
                .setBody("东遥课堂")
                .setOutTradeNo(orderNo)
                .setTotalFee(totalFee)
                .setTimeStart(timeStart)
                .setTimeExpire(timeExpire)
                .setNotifyUrl(payNotifyProperties.getPayNotifyUrl())
                .setSpbillCreateIp(realIp)
                .setTradeType("MWEB")
                .setOpenId(openId)
                .setSceneInfo("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://xcx.wegoo.cn\",\"wap_name\": \"东遥课堂\"}}")
                .setAttach("东遥课堂")
                .setPaternerKey(wxPayKey)//"612aa0e07599468997974318e654509f"
                .build();
        //获取微信返回的结果
        log.info("开始发送微信支付xml--->" + PaymentKit.toXml(params));
        String xmlResult = HttpsUtils.sendPost(WxConstans.PAY_URL, PaymentKit.toXml(params)).toString();
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
        String date = sdf_yMdHms.format(new Date());
        Map payResult = new HashMap();
        payResult.put("appId", gzhAppId);
        payResult.put("timeStamp", date);
        payResult.put("nonceStr", params.get("nonce_str"));
        payResult.put("package", "prepay_id=" + prepay_id);
        payResult.put("signType", "MD5");
        String paySign = PaymentKit.createSign(payResult, wxPayKey);
        payResult.put("paySign", paySign);
        WxPayVo wxPayVo = new WxPayVo();
        wxPayVo.setTimeStamp(date);
        wxPayVo.setNonceStr(params.get("nonce_str"));
        wxPayVo.setPackageStr("prepay_id=" + prepay_id);
        wxPayVo.setSignType("MD5");
        wxPayVo.setPaySign(payResult.get("paySign").toString());
//        1.需对redirect_url进行urlencode处理
//        2.由于设置redirect_url后,回跳指定页面的操作可能发生在：
//        1,微信支付中间页调起微信收银台后超过5秒
//        2,用户点击“取消支付“或支付完成后点“完成”按钮。因此无法保证页面回跳时，
//        支付流程已结束，所以商户设置的redirect_url地址不能自动执行查单操作，应让用户去点击按钮触发查单操作。回跳页面展示效果可参考下图
        wxPayVo.setMweb_url(mweb_url+"&redirect_url="+redirectUrl);
        wxPayVo.setAppid(gzhAppId);
        return wxPayVo;
    }


//    @Override
//    public void adminPay(@Validated(AdminPay.class) AdminPayVo adminPayVo) {
//        log.info("开始企业付款");
//        String openId = adminPayVo.getOpenId();
//        String realName = adminPayVo.getRealName();
//        Integer amount = adminPayVo.getAmount();
//        String desc = adminPayVo.getDesc();
//        String realIp = adminPayVo.getRealIp();
//        Map<String, String> params = WxAdminPay.New()
//                .setMch_appid(WxConstans.APPID)
//                .setMchid(WxConstans.MCHID)
//                .setPartner_trade_no("提现号")
//                .setOpenid(openId)
//                .setRe_user_name(realName)
//                .setAmount(amount)//企业付款金额，单位为分
//                .setDesc(desc)
//                .setSpbill_create_ip(realIp)
//                .setPaternerKey(WxConstans.PARTNERKEY)
//                .build();
//
//        //获取微信返回的结果
//        log.info("开始发送企业付款xml--->" + PaymentKit.toXml(params));
//        String xmlResult = HttpsUtils.sendPost(WxConstans.ADMIN_PAY_URL, PaymentKit.toXml(params)).toString();
//        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
//        log.info("结束发送企业付款xml--->" + result);
//        String return_code = result.get("return_code");
//        String return_msg = result.get("return_msg");
//        if (!PaymentKit.codeIsOK(return_code)) {
//            log.error("return_code>" + return_code + " return_msg>" + return_msg);
//            throw new RuntimeException(return_msg);
//        }
//        String result_code = result.get("result_code");
//        if (!PaymentKit.codeIsOK(result_code)) {
//            log.error("result_code>" + result_code + " return_msg>" + return_msg);
//            throw new RuntimeException(return_msg);
//        }
//        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
////        商户订单号	partner_trade_no	是	1217752501201407033233368018	String(32)	商户订单号，需保持历史全局唯一性(只能是字母或者数字，不能包含有符号)
////        微信订单号	payment_no	是	1007752501201407033233368018	String	企业付款成功，返回的微信订单号
////        微信支付成功时间	payment_time	是	2015-05-19 15：26：59	String	企业付款成功时间
//        String partner_trade_no = result.get("partner_trade_no");
//        String payment_no = result.get("payment_no");
//        String payment_time = result.get("payment_time");
//        log.info("商户订单号:" + partner_trade_no + " 企业付款成功，返回的微信订单号:" + payment_no + "企业付款成功时间" + payment_time);
//        //更新提现信息
//        int updateCount = withdrawService.updateByWithdrawId(partner_trade_no, partner_trade_no, payment_no);
//        if (0 == updateCount)
//            BSUtil.isTrue(false, "微信付款成功，更新数据库异常");
//    }

}
