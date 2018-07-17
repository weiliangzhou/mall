package com.zwl.service;

/**
 * @author 二师兄超级帅
 * @Title: 微信公众号获取token
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1116:40
 */
public interface WxAccessTokenService {
    public String getAccessToken(String merchantId);
}
