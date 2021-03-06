package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.vo.JsApiTokenVo;
import com.zwl.model.vo.WxH5AccessTokenVo;
import com.zwl.model.vo.WxUserInfoVo;
import com.zwl.model.vo.WxUserInfoVo2;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.service.H5AppWeChatService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class H5AppWeChatServiceImpl implements H5AppWeChatService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public WxH5AccessTokenVo getH5AccessToken(String appId, String secret, String code) {
        if (null == appId) {
            BSUtil.isTrue(Boolean.FALSE, "请输入微信公众号APPID");
        }
        if (null == secret) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要工作号 appsecret ");
        }
        if (null == code) {
            BSUtil.isTrue(Boolean.FALSE, "公众号授权code 不能为空");
        }
        // 获取接口URL
        String args = String.format("&appid=%s&secret=%s&code=%s", appId, secret, code);
        String resultStr = HttpsUtils.sendGet(WxConstans.GET_GZH_OPENID + args, null);
        if (null == resultStr) {
            BSUtil.isTrue(Boolean.FALSE, "微信用户不存在");
        }
        WxH5AccessTokenVo accessTokenVo = JSON.parseObject(resultStr, WxH5AccessTokenVo.class);
        return accessTokenVo;
    }

    @Override
    public WxUserInfoVo getWeChatUserInfo(String accessToken, String openid) {
        if (null == accessToken) {
            BSUtil.isTrue(Boolean.FALSE, "微信的accessToken不能为空");
        }
        if (null == openid) {
            BSUtil.isTrue(Boolean.FALSE, "微信的openid不能为空");
        }
        String args = String.format("&access_token=%s&openid=%s", accessToken, openid);
        String resultStr = HttpsUtils.sendGet(WxConstans.GET_WECHAT_USERINFO + args, null);
        if (null == resultStr) {
            BSUtil.isTrue(Boolean.FALSE, "微信用户信息不存在");
        }
        WxUserInfoVo userInfoVo = JSON.parseObject(resultStr, WxUserInfoVo.class);
        return userInfoVo;
    }

    @Override
    public String getWechatJsApiToken(String accessToken) {
        if (null == accessToken) {
            BSUtil.isTrue(Boolean.FALSE, "accessToken不能为空");
        }
        String jsTokenKey = String.format("jsapi_%s", accessToken);
        String jsToken = stringRedisTemplate.boundValueOps(jsTokenKey).get();
        if (StringUtils.isNotBlank(jsToken)) {
            return jsToken;
        }
        String args = String.format("?access_token=%s&type=jsapi", accessToken);
        String resultStr = HttpsUtils.sendGet(WxConstans.WECHAT_JSAPI + args, null);
        if (null == resultStr) {
            BSUtil.isTrue(Boolean.FALSE, "微信返回 jsApiToken 为空");
        }
        JsApiTokenVo tokenVo = JSON.parseObject(resultStr, JsApiTokenVo.class);
        if (tokenVo.getErrcode() == null || tokenVo.getErrcode() == 0) {
            //添加到缓存并且返回token
            stringRedisTemplate.boundValueOps(jsTokenKey).set(tokenVo.getTicket(), 5, TimeUnit.MINUTES);
            return tokenVo.getTicket();
        } else {
            log.error(String.format("微信获取jsapi tokent出错 微信返回信息:%s", resultStr));
            BSUtil.isTrue(Boolean.FALSE, "获取微信JSAPI Token 出错");
        }
        return null;
    }

    @Override
    public WxUserInfoVo2 getWeChatUserInfoIncludeSubscribe(String accessToken, String openid) {
        if (null == accessToken) {
            BSUtil.isTrue(Boolean.FALSE, "微信的accessToken不能为空");
        }
        if (null == openid) {
            BSUtil.isTrue(Boolean.FALSE, "微信的openid不能为空");
        }
        String args = String.format("&access_token=%s&openid=%s", accessToken, openid);
        String resultStr = HttpsUtils.sendGet(WxConstans.GET_WECHAT_USERINFO_INCLUDE_SUBSCRIBE + args, null);
        if (null == resultStr) {
            BSUtil.isTrue(Boolean.FALSE, "微信用户信息不存在");
        }
        WxUserInfoVo2 userInfoVo = JSON.parseObject(resultStr, WxUserInfoVo2.class);
        return userInfoVo;
    }
}
