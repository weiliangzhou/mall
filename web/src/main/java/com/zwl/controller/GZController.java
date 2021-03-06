package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.baseController.BaseController;
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
public class GZController extends BaseController {

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

    @PostMapping("/getGzhOpenId")
    public String getGzhOpenId(@RequestBody JSONObject jsonObject) {
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
        return setSuccessResult(openId);
    }

    @PostMapping("/getGzhJsApiToken")
    public Result getGzhJsApiToken(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String url = jsonObject.getString("url");
        WxJsApiTokenMessage wxJsApiTokenMessage = gzhService.getGzhJsApiToken(merchantId, url);
        return wxJsApiTokenMessage;
    }
}
