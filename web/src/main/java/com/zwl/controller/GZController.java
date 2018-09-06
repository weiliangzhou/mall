package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Merchant;
import com.zwl.model.vo.JsApiTokenVo;
import com.zwl.model.vo.WxJsApiTokenMessage;
import com.zwl.model.wxpay.HashKit;
import com.zwl.service.GZHService;
import com.zwl.service.H5AppWeChatService;
import com.zwl.service.MerchantService;
import com.zwl.service.WxAccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 二师兄超级帅
 * @Title: 获取公众号openid
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2415:12
 */
@RestController
@RequestMapping("/wx/gzh/")
@Slf4j
public class GZController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WxAccessTokenService wxAccessTokenService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private H5AppWeChatService h5AppWeChatService;
    @Autowired
    private GZHService gzhService;

    @PostMapping("/sendFormId")
    public Result saveFormIdToRedis(@RequestBody JSONObject jsonObject) {
        String formId = jsonObject.getString("formId");
        String userId = jsonObject.getString("userId");
        stringRedisTemplate.boundValueOps("formId_" + userId).set(formId, 7, TimeUnit.DAYS);
        Result result = new Result();
        return result;
    }

    @PostMapping("/getFormId")
    public Result getFormIdFromRedis(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String gzOpenId = stringRedisTemplate.boundValueOps("formId_" + userId).get();
        Result result = new Result();
        result.setData(StringUtils.isBlank(gzOpenId) ? "true" : "false");
        return result;
    }

    @PostMapping("/getGzhOpenId")
    public Result getGzhOpenId(@RequestBody JSONObject jsonObject) {
        String code = jsonObject.getString("code");
        String merchantId = jsonObject.getString("merchantId");
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        String gzhAppId = merchant.getGzAppId();
        String gzhKey = merchant.getGzAppKey();
        String openId = wxAccessTokenService.getGzhOpenId(merchantId, gzhAppId, gzhKey, code);
        if (StringUtils.isBlank(openId)) {
            log.error("获取公众号openId错误");
            BSUtil.isTrue(false, "系统异常，请稍后重试！");
        }
        Result result = new Result();
        result.setData(openId);
        return result;
    }

    //@RequestParam(value = "merchantId") String merchantId ,@RequestParam(value = "url") String url
    @PostMapping("/getGzhJsApiToken")
    public Result getGzhJsApiToken(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String url = jsonObject.getString("url");
        WxJsApiTokenMessage wxJsApiTokenMessage = gzhService.getGzhJsApiToken(merchantId, url);
        return wxJsApiTokenMessage;
    }
}
