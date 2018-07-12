package com.zwl.serviceimpl;

import com.zwl.service.WxAccessTokenService;
import com.zwl.util.HttpsUtils;
import com.zwl.wxpay.WxConstans;
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
public class WxAccessTokenServiceImpl implements WxAccessTokenService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getAccessToken() {
        //先查询redis是否存在accessToken
        //如果存在则直接返回
        //否则调用api接口获取
//        access_token的有效期目前为2个小时
        String accessToken = stringRedisTemplate.boundValueOps("accessToken").get();
        if (accessToken == null) {
            accessToken = HttpsUtils.sendGet(WxConstans.ACCESS_TOKEN,null);
            stringRedisTemplate.boundValueOps("accessToken").set(accessToken, 2, TimeUnit.HOURS);
        }
        return accessToken;
    }
}
