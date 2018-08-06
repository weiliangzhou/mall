package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.wxpay.WxConstans;
import com.zwl.service.QRCodeService;
import com.zwl.util.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by 二师兄超级帅 on 2018/8/5.
 */
@Service
@Slf4j
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


//        String pageTemp = page.replaceAll("/", "");

    @Override
    public String getQRCode(String userId, String page, String accessToken) {
        Map postMap = new HashMap();
        postMap.put("scene", userId);
        postMap.put("page", page);
        //先从redis缓存读取永久code   key=userId+page value=imageUrl
        String QRCode = stringRedisTemplate.boundValueOps(userId + "_qrcode_" + page).get();
//        log.info(QRCode);
        if (QRCode == null) {
            try {
                QRCode = HttpsUtils.httpPostWithJSON2(WxConstans.QR_CODE + accessToken, JSONObject.toJSONString(postMap));
                log.info("二维码url"+QRCode);
                stringRedisTemplate.boundValueOps(userId + "_qrcode_" + page).set(QRCode, 360, TimeUnit.DAYS);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return QRCode;
    }
}
