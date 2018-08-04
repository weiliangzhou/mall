package com.zwl.controller;

import com.zwl.model.po.Merchant;
import com.zwl.model.po.Order;
import com.zwl.model.wxpay.PaymentKit;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.service.MerchantService;
import com.zwl.service.OrderService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: 订单超时查询并更新数据库状态
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2414:02
 */
@Configuration
@EnableScheduling
@Slf4j
@RestController
@RequestMapping("/wx/task")
public class WxPayTask {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MerchantService merchantService;

//    每天凌晨2点
//    10分钟一次，测试数据
    @GetMapping("/task")
    @Scheduled(cron = "0 2 0 * * ?")
    public void queryOrderStatus() {
//        小程序ID	appid	是	String(32)	wxd678efh567hg6787	微信分配的小程序ID
//        商户号	mch_id	是	String(32)	1230000109	微信支付分配的商户号
//        微信订单号	transaction_id	二选一	String(32)	1009660380201506130728806387	微信的订单号，优先使用
//        商户订单号	out_trade_no	String(32)	20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
//        随机字符串	nonce_str	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	随机字符串，不长于32位。推荐随机数生成算法
//        签名	sign	是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	通过签名算法计算得出的签名值，详见签名生成算法
//        签名类型	sign_type	否	String(32)	HMAC-SHA256	签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
//        先查询未付款的订单列表
//        然后遍历查询
        Order orderQuery=new Order();
        orderQuery.setOrderStatus(0);
        List<Order> orderList=orderService.getOrderList(orderQuery);
        for (Order order:orderList){
            String orderNo=order.getOrderNo();
            String merchantId=order.getMerchantId();
            Merchant merchant=merchantService.getMerchantByMerchantId(merchantId);
            String appid=merchant.getAppId();
            String wxPayKey=merchant.getWxPayKey();
            Map queryMap=new HashMap();
            queryMap.put("appid",appid);
            queryMap.put("mch_id",merchantId);
            queryMap.put("out_trade_no",orderNo);
            queryMap.put("nonce_str",String.valueOf(System.currentTimeMillis()));
            String sign = PaymentKit.createSign(queryMap, wxPayKey);
            queryMap.put("sign",sign);
            log.info("开始发送查询微信支付订单xml--->" + PaymentKit.toXml(queryMap));
            String xmlResult = HttpsUtils.sendPost(WxConstans.QUERY_WX_ORDER, PaymentKit.toXml(queryMap)).toString();
            Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
            log.info("结束发送查询微信支付订单xml--->" + result);
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

//            以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，
//            如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。
            String trade_state=result.get("trade_state");
            String out_trade_no=result.get("out_trade_no");
            if("CLOSED".equals(trade_state)){
//                SUCCESS—支付成功
//                REFUND—转入退款
//                NOTPAY—未支付
//                CLOSED—已关闭
//                REVOKED—已撤销（刷卡支付）
//                USERPAYING--用户支付中
//                PAYERROR--支付失败(其他原因，如银行返回失败)
                Order order_t=new Order();
                order_t.setOrderNo(out_trade_no);
                order_t.setOrderStatus(-1);
                log.info("微信订单已经关闭开始更新本地订单"+out_trade_no);
                orderService.updateOrder(order);
                log.info("微信订单已经关闭结束更新本地订单"+out_trade_no);
            }
        }
    }
}
