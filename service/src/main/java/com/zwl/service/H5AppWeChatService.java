package com.zwl.service;

import com.zwl.model.vo.JsApiTokenVo;
import com.zwl.model.vo.WxH5AccessTokenVo;
import com.zwl.model.vo.WxUserInfoVo;

/**
 * H5微信程序
 */
public interface H5AppWeChatService {
    /**
     * h5获取用户的授权Token
     *
     * @param appId  公众号的唯一标识
     * @param secret 公众号的appsecret
     * @param code   填写微信授权获取的 Code
     */
    WxH5AccessTokenVo getH5AccessToken(String appId, String secret, String code);

    /**
     * 获取微信用户授权之后的用户信息
     *
     * @param accessToken 通过 微信 https://api.weixin.qq.com/sns/oauth2/access_token  获取
     * @param openid      微信用户的唯一标识
     */
    WxUserInfoVo getWeChatUserInfo(String accessToken, String openid);

    /**
     * 微信JSAPIToken
     *
     * @param accessToken 微信全局唯一的 accessToken
     * @return
     */
    JsApiTokenVo getWechatJsApiToken(String accessToken);
}
