package com.zwl.service;

import com.zwl.model.wxpay.AdminPayVo;

import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxPayService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1014:23
 */
public interface WxPayService {
    Map pay(String realIp, String openId, String orderNo, String totalFee);

    void adminPay(AdminPayVo adminPayVo);
}
