package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.vo.WxH5AccessTokenVo;
import com.zwl.model.vo.WxUserInfoVo;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.service.H5AppWeChatService;
import com.zwl.util.HttpsUtils;
import org.springframework.stereotype.Service;

@Service
public class H5AppWeChatServiceImpl implements H5AppWeChatService {

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
        WxUserInfoVo userInfoVo = JSON.parseObject(resultStr , WxUserInfoVo.class);
        return userInfoVo;
    }
}
