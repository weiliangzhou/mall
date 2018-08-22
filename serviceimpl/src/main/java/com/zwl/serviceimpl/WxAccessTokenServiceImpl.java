package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.service.WxAccessTokenService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 二师兄超级帅
 * @Title: WxAccessTokenServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1116:41
 */
@Service
@Slf4j
public class WxAccessTokenServiceImpl implements WxAccessTokenService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getAccessToken(String merchantId, String gzAppId, String gzAppKey,int type) {
        //先查询redis是否存在accessToken
        //如果存在则直接返回
        //否则调用api接口获取
        //access_token的有效期目前为2个小时
//        type 1=公众号 2=微信小程序
        String accessToken = stringRedisTemplate.boundValueOps("accessToken_" +merchantId+"_"+type).get();
        if (StringUtils.isBlank(accessToken)) {
            String result = HttpsUtils.sendGet(WxConstans.ACCESS_TOKEN + "&appid=" + gzAppId + "&secret=" + gzAppKey, null);
            JSONObject jsonObject = JSONObject.parseObject(result);
            accessToken = jsonObject.getString("access_token");
            stringRedisTemplate.boundValueOps("accessToken_" +merchantId+"_"+type).set(accessToken, 5, TimeUnit.MINUTES);
        }
        return accessToken;
    }

    @Override
    public String getGzhOpenId(String merchantId, String gzAppId, String gzAppKey, String code) {
//        String gzhOpenId = stringRedisTemplate.boundValueOps("gzhOpenId_"+code).get();
//        if (StringUtils.isBlank(gzhOpenId)) {
        String result = HttpsUtils.sendGet(WxConstans.GET_GZH_OPENID + "&appid=" + gzAppId + "&secret=" + gzAppKey + "&code=" + code, null);
        log.info("获取公众号OpenId"+result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String gzhOpenId = jsonObject.getString("openid");
//            stringRedisTemplate.boundValueOps("gzhOpenId_"+code).set(gzhOpenId, 5, TimeUnit.MINUTES);
//        }
        return gzhOpenId;
    }
}
