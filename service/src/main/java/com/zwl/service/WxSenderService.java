package com.zwl.service;

/**
 * @author 二师兄超级帅
 * @Title: WxSenderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1117:08
 */
public interface WxSenderService {
    public void sendBuyMsg(String openid, String productName, Integer price,String merchantId);
}
