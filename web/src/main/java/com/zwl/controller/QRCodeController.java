package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Merchant;
import com.zwl.model.po.User;
import com.zwl.model.po.UserInfo;
import com.zwl.service.*;
import com.zwl.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 二维码
 * Created by 二师兄超级帅 on 2018/8/4.
 */
@RestController
@RequestMapping("/wx/qr")
@Slf4j
public class QRCodeController {

    @Autowired
    private WxAccessTokenService wxAccessTokenService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/getQRCode")
    public String getQRCode(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String userId = jsonObject.getString("userId");
        String page = jsonObject.getString("page");
//        友好提示:如果分享出去，当前用户未购买学员以上等级产品，不能造成分佣，
        //User user = userService.getByUserId(userId);
        //Integer memberLevel = user.getMemberLevel();
        //if (memberLevel == null || memberLevel <= 4)
        //    BSUtil.isTrue(false, "您未购买学员产品，分享出去，可能不会给您带来收益！");
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        String appid = merchant.getAppId();
        String appSecret = merchant.getAppSecret();
        //type 1=公众号 2=微信小程序
        String accessToken = wxAccessTokenService.getAccessToken(merchantId, appid, appSecret, 2);
        String qrCode = qrCodeService.getQRCode(userId, page, accessToken);
        log.info("微信小程序accessToken" + accessToken);
        Result result = new Result();
        result.setData(qrCode);
        return JSON.toJSONString(result);

    }

    @PostMapping("/getH5QrCode")
    public Result getH5QrCode(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        if (userId == null)
            BSUtil.isTrue(false, "系统繁忙请稍后重试!");
        String qrUrl = stringRedisTemplate.boundValueOps(userId + "_h5qrcode").get();
        if (StringUtils.isBlank(qrUrl)) {
            String smallImage = QRCodeUtil.createQrCode("http://dy.xc2018.com.cn/home?referrer=" + userId, null, null);
            User user = userService.getByUserId(userId);
            UserInfo userInfo = userInfoService.getByUserId(userId);
            String userLogo = user.getLogoUrl()==null?"http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180911/6a989ec302994c6c98c2d4810f9fbcb2.png": user.getLogoUrl();
           String nickNameOrPhone=StringUtils.isBlank(userInfo.getNickName())?user.getRegisterMobile():userInfo.getNickName();
            try {
                qrUrl = QRCodeUtil.mergeImage("http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180913/adffdcc641104baeafc9d7276c03aacb.png", smallImage, "310", "380", userLogo, "200", "75",nickNameOrPhone);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringRedisTemplate.boundValueOps(userId + "_h5qrcode").set(qrUrl, 30, TimeUnit.DAYS);
        }
        log.info("userId:" + userId + "------二维码" + qrUrl);
        Result result = new Result();
        result.setData(qrUrl);
        return result;
    }

}
