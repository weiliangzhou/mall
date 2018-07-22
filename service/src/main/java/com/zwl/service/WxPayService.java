package com.zwl.service;

import com.zwl.model.wxpay.AdminPayVo;
import com.zwl.model.wxpay.WxPayVo;

import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxPayService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1014:23
 */
public interface WxPayService {
    WxPayVo pay(String realIp, String openId, String orderNo, String totalFee,String appid,String mch_id,String wxPayKey);

//    void adminPay(AdminPayVo adminPayVo);
}
